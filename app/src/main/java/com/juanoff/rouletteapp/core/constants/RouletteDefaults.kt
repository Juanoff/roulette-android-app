package com.juanoff.rouletteapp.core.constants

object RouletteDefaults {
    const val SPIN_SPEED_DEGREES_PER_SECOND = 900f
    const val MIN_SPIN_DURATION_MS = 2500
    const val MAX_SPIN_DURATION_MS = 5000
    const val MIN_SPIN_ROTATION = 1800
    const val MAX_SPIN_ROTATION = 3600
    const val DEFAULT_SECTOR_COUNT = 8

    val AVAILABLE_SECTOR_COUNTS = listOf(
        4,
        6,
        8,
        10,
        12,
    )
}
