package com.juanoff.rouletteapp.domain.usecase

import com.juanoff.rouletteapp.domain.model.RouletteSector
import javax.inject.Inject
import kotlin.math.absoluteValue

private const val FULL_ROTATION_DEGREES = 360f

class CalculateRouletteResultUseCase @Inject constructor() {
    operator fun invoke(
        sectors: List<RouletteSector>,
        angle: Float,
    ): RouletteSector {
        require(sectors.isNotEmpty()) {
            "Roulette sectors cannot be empty"
        }

        val normalizedAngle = (angle % FULL_ROTATION_DEGREES).absoluteValue
        val sectorSize = FULL_ROTATION_DEGREES / sectors.size
        val index = ((FULL_ROTATION_DEGREES - normalizedAngle) / sectorSize).toInt() % sectors.size

        return sectors[index]
    }
}