package com.example.matrixcontroller.presentation.tile

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.wear.activity.ConfirmationActivity
import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.TileService
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


const val host: String = "92.31.2.164"
const val urlStr: String = "http://$host"

class APICaller(context: Context) {
    private val queue: RequestQueue

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun updateHealth(state: MatrixControllerState) {
        val stringReq = StringRequest(
            Request.Method.GET,
            "$urlStr/health",
            {
                state.status = "Online"
                ActionBuilders.LoadAction.Builder().build()

            },
            {
                state.status = "Offline"
            }
        )
        queue.add(stringReq)
    }

    fun updateState(state: MatrixControllerState, context: Context) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "$urlStr/status",
            JSONObject(),
            {
                state.status = "Online"
                if (it.has("running") && it["running"] == true) {
                    for (key in state.programs.keys) {
                        state.programs[key] = (it["type"] == key)
                    }
                }
                else {
                    for (key in state.programs.keys) {
                        state.programs[key] = false
                    }
                }
                TileService.getUpdater(context)
                    .requestUpdate(MatrixControllerTileService::class.java)
            },
            {
                state.status = "Offline"
                for (key in state.programs.keys) {
                    state.programs[key] = false
                }
                TileService.getUpdater(context)
                    .requestUpdate(MatrixControllerTileService::class.java)
            }
        )
        queue.add(jsonObjectRequest)
    }


    fun switchApps(type: String, context: Context): Boolean? {
        val params: HashMap<String, String> = HashMap()
        params["type"] = type
        val requestBody = JSONObject()
        requestBody.put("type", type)
        var result: Boolean? = null
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$urlStr/run",
            requestBody,
            {
                result = true
                val intent = Intent(context, ConfirmationActivity::class.java).apply {
                    putExtra(
                        ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION
                    )
                    putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Started $type")
                    putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 2000)
                }
                startActivity(context, intent, null)
                (context as Activity).finish()
            },
            {
                result = false
                val intent = Intent(context, ConfirmationActivity::class.java).apply {
                    putExtra(
                        ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.FAILURE_ANIMATION,
                    )
                    putExtra(ConfirmationActivity.EXTRA_MESSAGE, it.cause?.message)
                    putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 5000)
                }
                startActivity(context, intent, null)
                (context as Activity).finish()
            }
        )
        queue.add(jsonObjectRequest)

        return result

    }

    fun stop(context: Context) {
        val stringRequest = StringRequest(
            Request.Method.POST,
            "$urlStr/stop",
            {
                val intent = Intent(context, ConfirmationActivity::class.java).apply {
                    putExtra(
                        ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION
                    )
                    putExtra(ConfirmationActivity.EXTRA_MESSAGE, "App stopped!")
                    putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 2000)
                }
                startActivity(context, intent, null)
                (context as Activity).finish()
            },
            {
                val intent = Intent(context, ConfirmationActivity::class.java).apply {
                    putExtra(
                        ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.FAILURE_ANIMATION,
                    )
                    putExtra(ConfirmationActivity.EXTRA_MESSAGE, it.cause?.message)
                    putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 5000)
                }
                startActivity(context, intent, null)
                (context as Activity).finish()
            }
        )
        queue.add(stringRequest)
    }


    companion object: SingletonHolder<APICaller, Context>(::APICaller)

}

open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}