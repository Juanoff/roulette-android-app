package com.juanoff.rouletteapp.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.withSave

fun DrawScope.drawSectorLabel(
    text: String,
    angle: Float,
    radius: Float,
    color: Color = Color.White,
) {
    drawContext.canvas.nativeCanvas.apply {
        withSave {
            rotate(
                angle,
                center.x,
                center.y,
            )

            drawText(
                text,
                center.x,
                center.y - radius,
                android.graphics.Paint().apply {
                    this.color = color.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 42f
                    isFakeBoldText = true
                    isAntiAlias = true
                },
            )
        }
    }
}