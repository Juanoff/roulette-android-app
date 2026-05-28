package com.juanoff.rouletteapp.ui.preview

import com.juanoff.rouletteapp.core.constants.RouletteDefaults
import com.juanoff.rouletteapp.domain.model.RouletteConfiguration
import com.juanoff.rouletteapp.domain.model.RouletteState
import com.juanoff.rouletteapp.domain.usecase.GenerateRouletteSectorsUseCase

object RoulettePreviewData {
    private val initialConfiguration = RouletteConfiguration(
        sectorCount = RouletteDefaults.DEFAULT_SECTOR_COUNT,
    )
    private val generateRouletteSectorsUseCase = GenerateRouletteSectorsUseCase()

    fun previewState(): RouletteState {
        return RouletteState(
            configuration = initialConfiguration,
            sectors = generateRouletteSectorsUseCase(
                initialConfiguration.sectorCount,
            ),
            currentRotation = 45f,
            isSpinning = false,
        )
    }
}