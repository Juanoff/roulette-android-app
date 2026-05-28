package com.juanoff.rouletteapp.domain.usecase

import com.juanoff.rouletteapp.domain.model.RouletteSector
import javax.inject.Inject

class GenerateRouletteSectorsUseCase @Inject constructor() {
    operator fun invoke(
        sectorCount: Int,
    ): List<RouletteSector> {
        return List(sectorCount) { index ->
            RouletteSector(
                id = index,
                label = ((index + 1) * 10).toString(),
                colorArgb = generateSectorColor(index),
            )
        }
    }

    private fun generateSectorColor(
        index: Int,
    ): Long {
        val colors = listOf(
            0xFFE57373,
            0xFF64B5F6,
            0xFF81C784,
            0xFFFFD54F,
            0xFFBA68C8,
            0xFFFF8A65,
            0xFFA1887F,
            0xFF4DB6AC,
            0xFF7986CB,
            0xFFDCE775,
            0xFFFFB74D,
            0xFF90A4AE,
        )
        return colors[index % colors.size]
    }
}
