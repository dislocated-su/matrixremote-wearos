package com.example.matrixcontroller.presentation.tile

import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.material.ButtonColors
import com.example.matrixcontroller.presentation.theme.tileColorPallete
import com.google.android.horologist.tiles.CoroutinesTileService
import java.util.*
import kotlin.collections.HashMap

private const val RESOURCES_VERSION = "0"

val buttonColors = ButtonColors.secondaryButtonColors(tileColorPallete)
val chipColors = androidx.wear.tiles.material.ChipColors.primaryChipColors(tileColorPallete)

class MatrixControllerTileService : CoroutinesTileService() {
    private lateinit var renderer: MatrixControllerRenderer
    private var tileState: MatrixControllerState = MatrixControllerState(
        mapOf(
            "scolt" to false,
            "pidisplay" to false
        ) as HashMap<String, Boolean>,
        "Tap refresh to ping"
    )

    lateinit var api: APICaller
    lateinit var myTimer: Timer
    override fun onCreate() {
        super.onCreate()
        renderer = MatrixControllerRenderer(context = this@MatrixControllerTileService)
        this.api = APICaller.getInstance(this)
        myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                api.updateState(tileState, this@MatrixControllerTileService)
            }
        }, 50, 5000)
    }

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return renderer.produceRequestedResources(mapOf(), requestParams)
    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        return renderer.renderTimeline(tileState, requestParams)
    }
}