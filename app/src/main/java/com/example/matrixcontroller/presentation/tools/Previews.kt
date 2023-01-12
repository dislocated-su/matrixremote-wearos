package com.example.matrixcontroller.presentation.tools

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.ModifiersBuilders

val emptyClickable = ModifiersBuilders.Clickable.Builder()
    .setOnClick(ActionBuilders.LoadAction.Builder().build())
    .setId("")
    .build()

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true,
    group = "Devices - Small Round",
)
annotation class WearDevicePreview

@Preview(
    backgroundColor = 0xff000000,
    showBackground = true,
    widthDp = 100,
    heightDp = 100
)
annotation class IconSizePreview