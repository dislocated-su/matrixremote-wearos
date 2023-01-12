package com.example.matrixcontroller.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.matrixcontroller.presentation.tile.APICaller

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (intent.extras?.getString(EXTRA_JOURNEY)) {
                    EXTRA_JOURNEY_SCOLT -> {
                        Text("Starting scolt")
                        APICaller.getInstance(this@MainActivity).switchApps("scolt", this@MainActivity)
                    }
                    EXTRA_JOURNEY_PIDISPLAY -> {
                        Text("Starting Pidisplay")
                        APICaller.getInstance(this@MainActivity).switchApps("pidisplay", this@MainActivity)
                    }
                    EXTRA_JOURNEY_STOP -> {
                        Text("Stopping current app")
                        APICaller.getInstance(this@MainActivity).stop(this@MainActivity)
                    }
                    else -> Text("Opened from app launcher")
                }
            }
        }
    }

    companion object {
        internal const val EXTRA_JOURNEY = "journey"
        internal const val EXTRA_JOURNEY_SCOLT = "journey:conversation"
        internal const val EXTRA_JOURNEY_PIDISPLAY = "journey:search"
        internal const val EXTRA_JOURNEY_STOP = "journey:stop"
    }
}
