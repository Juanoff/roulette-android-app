package com.juanoff.rouletteapp.core.util

import com.juanoff.rouletteapp.core.constants.RouletteDefaults
import kotlin.math.roundToInt

object AnimationDurationCalculator {
    fun calculate(
        rotationDegrees: Float,
    ): Int {
        val duration = (rotationDegrees / RouletteDefaults.SPIN_SPEED_DEGREES_PER_SECOND) * 1000f
        return duration
            .roundToInt()
            .coerceIn(
                RouletteDefaults.MIN_SPIN_DURATION_MS,
                RouletteDefaults.MAX_SPIN_DURATION_MS,
            )
    }
}