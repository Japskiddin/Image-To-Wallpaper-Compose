package io.github.japskiddin.imagetowallpaper.data

class SettingsRepository {
    fun fetchAspectRatio(): AspectRatio {
        return AspectRatio.Ratio4To3
    }
}