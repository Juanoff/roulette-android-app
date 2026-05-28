package com.juanoff.rouletteapp.domain.model

data class RouletteSpinState(
    val startRotation: Float,
    val targetRotation: Float,
    val startedAtMillis: Long,
    val durationMillis: Int,
)