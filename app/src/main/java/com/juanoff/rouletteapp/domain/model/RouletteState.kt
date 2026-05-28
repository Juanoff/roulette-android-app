package com.juanoff.rouletteapp.domain.model

import com.juanoff.rouletteapp.core.constants.RouletteDefaults

data class RouletteState(
    val sectors: List<RouletteSector> = emptyList(),
    val configuration: RouletteConfiguration = RouletteConfiguration(
        RouletteDefaults.DEFAULT_SECTOR_COUNT
    ),
    val currentRotation: Float = 0f,
    val isSpinning: Boolean = false,
    val selectedSector: RouletteSector? = null,
    val spinState: RouletteSpinState? = null,
)