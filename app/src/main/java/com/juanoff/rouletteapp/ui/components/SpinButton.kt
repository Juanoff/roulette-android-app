package com.juanoff.rouletteapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val ButtonHeight = 56.dp

@Composable
fun SpinButton(
    isSpinning: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = !isSpinning,
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(),
    ) {
        if (isSpinning) {
            CircularProgressIndicator()
        } else {
            Text(text = "SPIN")
        }
    }
}