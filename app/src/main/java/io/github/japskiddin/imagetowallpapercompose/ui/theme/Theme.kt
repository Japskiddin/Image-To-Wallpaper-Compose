package io.github.japskiddin.imagetowallpapercompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.SettingsViewModel

private val DarkColorScheme = darkColorScheme(
    primary = BlueEyes,
    background = Thunder,
    surface = Color.Black,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BlueEyes,
    background = Iron,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ImageToWallpaperTheme(
    viewModel: SettingsViewModel = hiltViewModel(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val themeState by viewModel.themeState.collectAsState()

    val colorScheme = when (themeState.theme) {
        AppTheme.MODE_SYSTEM -> {
            val isSystemNightMode = isSystemInDarkTheme()
            if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (isSystemNightMode)
                    dynamicDarkColorScheme(context)
                else
                    dynamicLightColorScheme(context)
            } else {
                if (isSystemNightMode) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                }
            }
        }

        AppTheme.MODE_NIGHT -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}