package com.juanoff.rouletteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juanoff.rouletteapp.ui.preview.RoulettePreviewData
import com.juanoff.rouletteapp.ui.roulette.RouletteRoute
import com.juanoff.rouletteapp.ui.roulette.RouletteScreen
import com.juanoff.rouletteapp.ui.theme.RouletteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RouletteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RouletteRoute()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun RouletteScreenPreview() {
    RouletteAppTheme {
        RouletteScreen(
            state = RoulettePreviewData.previewState(),
            onSpinClick = {},
            onSpinFinished = {},
            currentSectorCount = 8,
            availableSectorCounts = emptyList(),
            onSectorCountChanged = {},
        )
    }
}