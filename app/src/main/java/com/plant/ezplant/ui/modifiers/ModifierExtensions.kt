package com.plant.ezplant.ui.modifiers

import android.text.Layout.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.plant.ezplant.ui.theme.GreenBorder

fun Modifier.cardStyle(): Modifier {
    return this
        .width(10.dp)
        .height(200.dp)
        .border(2.dp, GreenBorder, RoundedCornerShape(12.dp))
}

fun Modifier.backgroundTiledImage(painter: Painter): Modifier = drawBehind {
    val tileSize = painter.intrinsicSize

    if (tileSize.width <= 0f || tileSize.height <= 0f) return@drawBehind

    val columns = (size.width / tileSize.width).toInt() +1
    val rows = (size.height / tileSize.height).toInt() +1

    for (x in 0 until columns) {
        for (y in 0 until rows) {
            withTransform({
                translate(left = x * tileSize.width + 2f, top = y * tileSize.height)
            }) {
                with(painter) {
                    draw(size = tileSize, alpha = 0.2f)
                }
            }
        }
    }
}
