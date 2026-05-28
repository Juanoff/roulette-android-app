package com.juanoff.rouletteapp.ui.roulette

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.juanoff.rouletteapp.core.constants.RouletteDefaults
import com.juanoff.rouletteapp.ui.event.RouletteUiEvent
import com.juanoff.rouletteapp.ui.viewmodel.RouletteViewModel

@Composable
fun RouletteRoute(
    viewModel: RouletteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    RouletteScreen(
        state = state,
        onSpinClick = { viewModel.onEvent(RouletteUiEvent.SpinClicked) },
        onSpinFinished = { viewModel.onEvent(RouletteUiEvent.SpinAnimationFinished) },
        onSectorCountChanged = {
            viewModel.onEvent(RouletteUiEvent.SectorCountChanged(it))
        },
        availableSectorCounts = RouletteDefaults.AVAILABLE_SECTOR_COUNTS,
        currentSectorCount = state.configuration.sectorCount,
    )
}