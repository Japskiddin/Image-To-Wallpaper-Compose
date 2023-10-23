package io.github.japskiddin.imagetowallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.japskiddin.imagetowallpaper.ui.theme.ImageToWallpaperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageToWallpaperTheme {
                AppScreen()
            }
        }
    }
}