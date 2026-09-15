package com.karthik.mycards.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Indigo40,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE1E3FF),
    onPrimaryContainer = Color(0xFF1A1A5E),
    secondary = Teal40,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = Color(0xFF1A1C22),
    surface = LightSurface,
    onSurface = Color(0xFF1A1C22),
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF5A6072),
)

private val DarkColorScheme = darkColorScheme(
    primary = Indigo80,
    onPrimary = Color(0xFF1A1A5E),
    primaryContainer = Color(0xFF2C2C72),
    onPrimaryContainer = Color(0xFFE1E3FF),
    secondary = Teal80,
    onSecondary = Color(0xFF00332E),
    background = DarkBackground,
    onBackground = Color(0xFFE6E8EF),
    surface = DarkSurface,
    onSurface = Color(0xFFE6E8EF),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFAEB4C2),
)

@Composable
fun MyCardsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We use our own brand colors instead of the phone's wallpaper colors,
    // so the app looks the same on every device.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
