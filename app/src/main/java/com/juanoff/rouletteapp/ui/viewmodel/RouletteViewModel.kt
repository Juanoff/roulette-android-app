package com.juanoff.rouletteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.juanoff.rouletteapp.core.constants.RouletteDefaults
import com.juanoff.rouletteapp.core.util.AnimationDurationCalculator
import com.juanoff.rouletteapp.domain.model.RouletteConfiguration
import com.juanoff.rouletteapp.domain.model.RouletteSpinState
import com.juanoff.rouletteapp.domain.model.RouletteState
import com.juanoff.rouletteapp.domain.usecase.CalculateRouletteResultUseCase
import com.juanoff.rouletteapp.domain.usecase.GenerateRouletteSectorsUseCase
import com.juanoff.rouletteapp.domain.usecase.GenerateSpinAngleUseCase
import com.juanoff.rouletteapp.ui.event.RouletteUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val calculateRouletteResultUseCase: CalculateRouletteResultUseCase,
    private val generateSpinAngleUseCase: GenerateSpinAngleUseCase,
    private val generateRouletteSectorsUseCase: GenerateRouletteSectorsUseCase,
) : ViewModel() {
    private val initialConfiguration = RouletteConfiguration(
        sectorCount = RouletteDefaults.DEFAULT_SECTOR_COUNT,
    )
    private val _state = MutableStateFlow(
        RouletteState(
            configuration = initialConfiguration,
            sectors = generateRouletteSectorsUseCase(
                initialConfiguration.sectorCount,
            ),
        )
    )

    val state: StateFlow<RouletteState> = _state.asStateFlow()

    fun onEvent(
        event: RouletteUiEvent,
    ) {
        when (event) {
            is RouletteUiEvent.SpinClicked -> {
                spinRoulette()
            }

            is RouletteUiEvent.SpinAnimationFinished -> {
                onSpinAnimationFinished()
            }

            is RouletteUiEvent.SectorCountChanged -> {
                updateSectorCount(event.count)
            }
        }
    }

    private fun spinRoulette() {
        if (_state.value.isSpinning) {
            return
        }

        val startRotation = _state.value.currentRotation
        val rotationDelta = generateSpinAngleUseCase()
        val targetRotation = startRotation + rotationDelta
        val duration = AnimationDurationCalculator.calculate(rotationDelta)

        val winner = calculateRouletteResultUseCase(
            sectors = _state.value.sectors,
            angle = targetRotation,
        )

        _state.value = _state.value.copy(
            isSpinning = true,
            selectedSector = null,
            spinState = RouletteSpinState(
                startRotation = startRotation,
                targetRotation = targetRotation,
                startedAtMillis = System.currentTimeMillis(),
                durationMillis = duration,
            ),
            currentRotation = targetRotation,
        )

        _state.value = _state.value.copy(
            selectedSector = winner,
        )
    }

    private fun onSpinAnimationFinished() {
        _state.value = _state.value.copy(
            isSpinning = false,
            spinState = null,
        )
    }

    private fun updateSectorCount(
        sectorCount: Int,
    ) {
        if (_state.value.isSpinning) {
            return
        }

        val configuration = RouletteConfiguration(
            sectorCount = sectorCount,
        )

        _state.value = _state.value.copy(
            configuration = configuration,
            sectors = generateRouletteSectorsUseCase(
                sectorCount,
            ),
            selectedSector = null,
        )
    }
}