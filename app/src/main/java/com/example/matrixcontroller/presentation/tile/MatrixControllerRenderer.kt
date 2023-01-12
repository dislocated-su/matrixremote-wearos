@file:OptIn(ExperimentalHorologistTilesApi::class)

package com.example.matrixcontroller.presentation.tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.wear.tiles.ColorBuilders
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.ModifiersBuilders.Clickable
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.material.Button
import androidx.wear.tiles.material.ButtonColors
import androidx.wear.tiles.material.CompactChip
import androidx.wear.tiles.material.Text
import androidx.wear.tiles.material.layouts.MultiButtonLayout
import androidx.wear.tiles.material.layouts.PrimaryLayout
import com.example.matrixcontroller.R
import com.example.matrixcontroller.presentation.theme.Teal200
import com.example.matrixcontroller.presentation.theme.tileColorPallete
import com.example.matrixcontroller.presentation.theme.wearColorPalette
import com.example.matrixcontroller.presentation.tools.IconSizePreview
import com.example.matrixcontroller.presentation.tools.WearDevicePreview
import com.example.matrixcontroller.presentation.tools.emptyClickable
import com.google.android.horologist.compose.tools.LayoutElementPreview
import com.google.android.horologist.compose.tools.TileLayoutPreview
import com.google.android.horologist.tiles.ExperimentalHorologistTilesApi
import com.google.android.horologist.tiles.images.drawableResToImageResource
import com.google.android.horologist.tiles.render.SingleTileLayoutRenderer

class MatrixControllerRenderer(context: Context) :
    SingleTileLayoutRenderer<MatrixControllerState, Map<String, Int>>(context) {
    override fun renderTile(
        state: MatrixControllerState,
        deviceParameters: DeviceParametersBuilders.DeviceParameters
    ): LayoutElementBuilders.LayoutElement {
        return tileLayout(
            context = context,
            deviceParameters = deviceParameters,
            state = state,
            scoltClickable = launchActivityClickable("scolt", toggleScolt()),
            pidisplayClickable = launchActivityClickable("pidisplay", togglePidisplay()),
            reloadClickable = loadActivityClickable("refresh"),
            stopClickable = launchActivityClickable("stop", displayStop())
        )
    }

    override fun ResourceBuilders.Resources.Builder.produceRequestedResources(
        resourceState: Map<String, Int>,
        deviceParameters: DeviceParametersBuilders.DeviceParameters,
        resourceIds: MutableList<String>
    ) {
        addIdToImageMapping(ID_IC_CLOCK,
            drawableResToImageResource(R.drawable.ic_baseline_schedule_24))
        addIdToImageMapping(ID_IC_CUBE,
            drawableResToImageResource(R.drawable.ic_baseline_view_in_ar_24))
    }

    companion object {
        internal const val ID_IC_CLOCK = "ic_baseline_schedule_24"
        internal const val ID_IC_CUBE = "ic_baseline_view_in_ar_24"
    }
}

private fun tileLayout(
    context: Context,
    deviceParameters: DeviceParametersBuilders.DeviceParameters,
    state: MatrixControllerState,
    scoltClickable: Clickable,
    pidisplayClickable: Clickable,
    reloadClickable: Clickable,
    stopClickable: Clickable
): PrimaryLayout {
    return PrimaryLayout.Builder(deviceParameters)
        .setPrimaryLabelTextContent(
            Text.Builder(context, state.status)
                .setTypography(androidx.wear.tiles.material.Typography.TYPOGRAPHY_TITLE3)
                .setColor(
                    ColorBuilders.ColorProp.Builder()
                        .setArgb(when (state.status.lowercase()) {
                            "online" -> Teal200.toArgb()
                            else -> wearColorPalette.primary.toArgb()
                        })
                        .build()
                )
                .build()
        )
        .setContent(
            MultiButtonLayout.Builder()
                .addButtonContent(
                    buttonLayout(state, context, scoltClickable, stopClickable, "scolt", MatrixControllerRenderer.ID_IC_CUBE)
                )
                .addButtonContent(
                    buttonLayout(state, context, pidisplayClickable, stopClickable,"pidisplay", MatrixControllerRenderer.ID_IC_CLOCK)
                )
                .build()
        )
        .setPrimaryChipContent(
            CompactChip.Builder(context, "Refresh",  reloadClickable, deviceParameters)
                .setChipColors(chipColors)
                .build()
        )
        .build()
}

private fun buttonLayout(
    state: MatrixControllerState,
    context: Context,
    clickable: Clickable,
    stopClickable: Clickable,
    toLaunch: String,
    iconID: String
) = Button.Builder(context,
when (state.programs[toLaunch]) {
        true -> stopClickable
        else -> clickable
    })
    .setContentDescription("Toggle $toLaunch")
    .setIconContent(iconID)
    .setButtonColors(
        when (state.programs[toLaunch]) {
            true -> ButtonColors.primaryButtonColors(tileColorPallete)
            else -> buttonColors
        }
    )
    .build()


@WearDevicePreview
@Composable
fun MatrixControllerPreview() {
    val state = MatrixControllerState(
        mapOf(
            "scolt" to true,
            "pidisplay" to false
        ) as HashMap<String, Boolean>,
        "Online"
    )
    val context = LocalContext.current
    TileLayoutPreview(
        state = state,
        resourceState= mapOf(),
        renderer = MatrixControllerRenderer(context)
    )
}

@IconSizePreview
@Composable
private fun ClockButtonPreview() {
    LayoutElementPreview(
        buttonLayout(
            context = LocalContext.current,
            clickable = emptyClickable,
            state = MatrixControllerState(
                mapOf(
                    "scolt" to true,
                    "pidisplay" to false
                ) as HashMap<String, Boolean>,
                "Online"
            ),
            toLaunch = "pidisplay",
            iconID = MatrixControllerRenderer.ID_IC_CLOCK,
            stopClickable = emptyClickable
        )
    ) {
        addIdToImageMapping(
            MatrixControllerRenderer.ID_IC_CLOCK,
            drawableResToImageResource(R.drawable.ic_baseline_schedule_24)
        )
    }
}

@IconSizePreview
@Composable
private fun CubeButtonPreview() {
    LayoutElementPreview(
        buttonLayout(
            context = LocalContext.current,
            clickable = emptyClickable,
            state = MatrixControllerState(
                mapOf(
                    "scolt" to true,
                    "pidisplay" to false
                ) as HashMap<String, Boolean>,
                "Online"
            ),
            toLaunch = "scolt",
            iconID = MatrixControllerRenderer.ID_IC_CUBE,
            stopClickable = emptyClickable
        )
    ) {
        addIdToImageMapping(
            MatrixControllerRenderer.ID_IC_CUBE,
            drawableResToImageResource(R.drawable.ic_baseline_view_in_ar_24)
        )
    }
}