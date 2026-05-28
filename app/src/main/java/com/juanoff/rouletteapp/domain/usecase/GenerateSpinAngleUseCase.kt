package com.juanoff.rouletteapp.domain.usecase

import com.juanoff.rouletteapp.core.constants.RouletteDefaults
import javax.inject.Inject
import kotlin.random.Random

class GenerateSpinAngleUseCase @Inject constructor() {
    operator fun invoke(): Float {
        return Random
            .nextInt(
                RouletteDefaults.MIN_SPIN_ROTATION,
                RouletteDefaults.MAX_SPIN_ROTATION
            )
            .toFloat()
    }
}