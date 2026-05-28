package com.juanoff.rouletteapp.ui.roulette

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.juanoff.rouletteapp.core.animation.RouletteAnimationStateCalculator
import com.juanoff.rouletteapp.domain.model.RouletteSector
import com.juanoff.rouletteapp.domain.model.RouletteState
import com.juanoff.rouletteapp.ui.components.RouletteWheel
import com.juanoff.rouletteapp.ui.components.SectorCountSelector
import com.juanoff.rouletteapp.ui.components.SpinButton

private val ScreenPadding = 24.dp
private val ContentSpacing = 24.dp

private const val UnknownResult = "?"

@Composable
fun RouletteScreen(
    state: RouletteState,
    onSpinClick: () -> Unit,
    onSpinFinished: () -> Unit,
    currentSectorCount: Int,
    availableSectorCounts: List<Int>,
    onSectorCountChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val animatedRotation = remember {
        Animatable(state.currentRotation)
    }

    LaunchedEffect(state.spinState) {
        val spinState = state.spinState ?: return@LaunchedEffect

        val animationState = RouletteAnimationStateCalculator.calculate(spinState = spinState)
        animatedRotation.snapTo(animationState.currentRotation)
        animatedRotation.animateTo(
            targetValue = spinState.targetRotation,
            animationSpec = tween(
                durationMillis = animationState.remainingDuration,
                easing = FastOutSlowInEasing,
            ),
        )

        onSpinFinished()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        if (isLandscape) {
            LandscapeRouletteLayout(
                rotationAngle = animatedRotation.value,
                sectors = state.sectors,
                selectedSector = state.selectedSector.takeIf { !state.isSpinning },
                isSpinning = state.isSpinning,
                onSpinClick = onSpinClick,
                paddingValues = innerPadding,
                currentSectorCount = currentSectorCount,
                availableSectorCounts = availableSectorCounts,
                onSectorCountChanged = onSectorCountChanged,
            )
        } else {
            PortraitRouletteLayout(
                rotationAngle = animatedRotation.value,
                sectors = state.sectors,
                selectedSector = state.selectedSector.takeIf { !state.isSpinning },
                isSpinning = state.isSpinning,
                onSpinClick = onSpinClick,
                paddingValues = innerPadding,
                currentSectorCount = currentSectorCount,
                availableSectorCounts = availableSectorCounts,
                onSectorCountChanged = onSectorCountChanged,
            )
        }
    }
}

@Composable
private fun PortraitRouletteLayout(
    rotationAngle: Float,
    sectors: List<RouletteSector>,
    selectedSector: RouletteSector?,
    isSpinning: Boolean,
    onSpinClick: () -> Unit,
    paddingValues: PaddingValues,
    currentSectorCount: Int,
    availableSectorCounts: List<Int>,
    onSectorCountChanged: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SectorCountSelector(
            currentCount = currentSectorCount,
            availableCounts = availableSectorCounts,
            onSectorCountSelected = onSectorCountChanged,
        )

        Spacer(modifier = Modifier.height(ContentSpacing))

        RouletteWheelSection(
            sectors = sectors,
            rotationAngle = rotationAngle,
        )

        Spacer(modifier = Modifier.height(ContentSpacing))

        RouletteInfoSection(
            selectedSector = selectedSector,
            isSpinning = isSpinning,
            onSpinClick = onSpinClick,
        )
    }
}

@Composable
private fun LandscapeRouletteLayout(
    rotationAngle: Float,
    sectors: List<RouletteSector>,
    selectedSector: RouletteSector?,
    isSpinning: Boolean,
    onSpinClick: () -> Unit,
    paddingValues: PaddingValues,
    currentSectorCount: Int,
    availableSectorCounts: List<Int>,
    onSectorCountChanged: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(ScreenPadding),
        horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RouletteWheelSection(
            sectors = sectors,
            rotationAngle = rotationAngle,
            modifier = Modifier.weight(1.15f),
        )

        Column(
            modifier = Modifier
                .weight(0.85f),
            verticalArrangement = Arrangement.spacedBy(ContentSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectorCountSelector(
                currentCount = currentSectorCount,
                availableCounts = availableSectorCounts,
                onSectorCountSelected = onSectorCountChanged,
            )

            RouletteInfoSection(
                selectedSector = selectedSector,
                isSpinning = isSpinning,
                onSpinClick = onSpinClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun RouletteWheelSection(
    sectors: List<RouletteSector>,
    rotationAngle: Float,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val wheelSize = remember(
            maxWidth,
            maxHeight,
        ) {
            minOf(
                maxWidth * 0.85f,
                maxHeight * 0.75f,
                420.dp,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(ContentSpacing))

            Box(
                modifier = Modifier.size(wheelSize),
                contentAlignment = Alignment.Center,
            ) {
                RouletteWheel(
                    sectors = sectors,
                    rotationAngle = rotationAngle,
                )

                RoulettePointer(
                    wheelSize = wheelSize,
                )
            }
        }
    }
}

@Composable
private fun RouletteInfoSection(
    selectedSector: RouletteSector?,
    isSpinning: Boolean,
    onSpinClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SelectedSectorCard(
            selectedSector = selectedSector,
        )

        Spacer(modifier = Modifier.height(ContentSpacing))

        SpinButton(
            isSpinning = isSpinning,
            onClick = onSpinClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BoxScope.RoulettePointer(
    wheelSize: Dp,
) {
    val pointerWidth = wheelSize * 0.02f
    val pointerHeight = wheelSize * 0.18f

    Box(
        modifier = Modifier
            .size(
                width = pointerWidth,
                height = pointerHeight,
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape,
            )
            .align(Alignment.TopCenter),
    )
}

@Composable
private fun SelectedSectorCard(
    selectedSector: RouletteSector?,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Selected Sector",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = selectedSector?.label ?: UnknownResult,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = selectedSector?.let { Color(it.colorArgb) } ?: Color.Unspecified,
            )
        }
    }
}