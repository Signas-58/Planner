package com.example.planner.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    secondary = RedSecondary,
    tertiary = RedTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = OnDark,
    onSecondary = OnDark,
    onTertiary = OnDark,
    onBackground = OnDark,
    onSurface = OnDark
)

@Composable
fun PlannerTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
