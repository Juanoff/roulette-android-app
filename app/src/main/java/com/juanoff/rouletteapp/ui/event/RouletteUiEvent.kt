package com.juanoff.rouletteapp.ui.event

sealed interface RouletteUiEvent {

    data object SpinClicked : RouletteUiEvent

    data object SpinAnimationFinished : RouletteUiEvent

    data class SectorCountChanged(
        val count: Int,
    ) : RouletteUiEvent
}