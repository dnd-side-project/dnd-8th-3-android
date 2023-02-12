package com.d83t.bpm.presentation.compose.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.d83t.bpm.presentation.compose.theme.*

private val ColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Color.White,
    background = Color.White,
    surface = Color.White
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BPMTheme(
    content: @Composable () -> Unit
) {
    val colors = ColorPalette
    CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}