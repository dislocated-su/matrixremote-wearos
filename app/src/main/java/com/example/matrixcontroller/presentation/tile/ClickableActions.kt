package com.example.matrixcontroller.presentation.tile

import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.ActionBuilders.AndroidActivity
import androidx.wear.tiles.ModifiersBuilders
import com.example.matrixcontroller.presentation.MainActivity

/**
 * Creates a Clickable that can be used to launch an activity.
 */
internal fun loadActivityClickable(
    clickableId: String
) = ModifiersBuilders.Clickable.Builder()
    .setId(clickableId)
    .setOnClick(ActionBuilders.LoadAction.Builder().build())
    .build()

internal fun launchActivityClickable(
    clickableId: String,
    androidActivity: AndroidActivity
) = ModifiersBuilders.Clickable.Builder()
    .setId(clickableId)
    .setOnClick(ActionBuilders.LaunchAction.Builder()
        .setAndroidActivity(androidActivity)
        .build()
    )
    .build()

internal fun toggleScolt() = AndroidActivity.Builder()
    .setMatrixActivity()
    .addKeyToExtraMapping(
        MainActivity.EXTRA_JOURNEY,
        ActionBuilders.stringExtra(MainActivity.EXTRA_JOURNEY_SCOLT)
    )
    .build()

internal fun togglePidisplay() = AndroidActivity.Builder()
    .setMatrixActivity()
    .addKeyToExtraMapping(
        MainActivity.EXTRA_JOURNEY,
        ActionBuilders.stringExtra(MainActivity.EXTRA_JOURNEY_PIDISPLAY)
    )
    .build()

internal fun displayStop() = AndroidActivity.Builder()
    .setMatrixActivity()
    .addKeyToExtraMapping(
        MainActivity.EXTRA_JOURNEY,
        ActionBuilders.stringExtra(MainActivity.EXTRA_JOURNEY_STOP)
    )
    .build()

internal fun AndroidActivity.Builder.setMatrixActivity(): AndroidActivity.Builder {
    return setPackageName("com.example.matrixcontroller")
        .setClassName("com.example.matrixcontroller.presentation.MainActivity")
}