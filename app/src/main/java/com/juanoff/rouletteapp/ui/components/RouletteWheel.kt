package com.juanoff.rouletteapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.juanoff.rouletteapp.domain.model.RouletteSector

private const val FULL_ROTATION_DEGREES = 360f
private const val START_ANGLE_OFFSET = -90f

@Composable
fun RouletteWheel(
    sectors: List<RouletteSector>,
    rotationAngle: Float,
    modifier: Modifier = Modifier,
) {
    val sectorSweepAngle = remember(sectors.size) {
        FULL_ROTATION_DEGREES / sectors.size
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {
        rotate(rotationAngle) {
            sectors.forEachIndexed { index, sector ->
                val startAngle = (index * sectorSweepAngle) + START_ANGLE_OFFSET

                drawArc(
                    color = Color(sector.colorArgb),
                    startAngle = startAngle,
                    sweepAngle = sectorSweepAngle,
                    useCenter = true,
                    size = Size(size.width, size.height),
                    topLeft = Offset.Zero,
                )

                drawSectorLabel(
                    text = sector.label,
                    angle = startAngle + (sectorSweepAngle / 2f) + 90f,
                    radius = size.minDimension * 0.35f,
                )
            }
        }
    }
}