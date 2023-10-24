package io.github.japskiddin.imagetowallpapercompose.data

class SettingsRepository {
    fun fetchAspectRatio(): AspectRatio {
        return AspectRatio.Ratio4To3
    }
}