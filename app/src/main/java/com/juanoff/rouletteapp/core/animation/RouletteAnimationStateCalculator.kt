package com.juanoff.rouletteapp.core.animation

import com.juanoff.rouletteapp.domain.model.RouletteSpinState

object RouletteAnimationStateCalculator {
    fun calculate(
        spinState: RouletteSpinState,
        currentTimeMillis: Long = System.currentTimeMillis(),
    ): RouletteAnimationState {
        val elapsed = currentTimeMillis - spinState.startedAtMillis
        val remainingDuration = (spinState.durationMillis - elapsed)
            .coerceAtLeast(0L)
            .toInt()

        val totalDistance = spinState.targetRotation - spinState.startRotation
        val progress = elapsed.toFloat() / spinState.durationMillis
        val currentRotation = spinState.startRotation + (totalDistance * progress.coerceIn(0f, 1f))

        return RouletteAnimationState(
            currentRotation = currentRotation,
            remainingDuration = remainingDuration,
        )
    }
}