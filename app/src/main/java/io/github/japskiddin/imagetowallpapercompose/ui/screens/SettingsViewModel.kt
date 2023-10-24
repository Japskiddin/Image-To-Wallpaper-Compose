package io.github.japskiddin.imagetowallpapercompose.ui.screens

import androidx.lifecycle.ViewModel
import io.github.japskiddin.imagetowallpapercompose.data.AspectRatio
import io.github.japskiddin.imagetowallpapercompose.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val repository: SettingsRepository = SettingsRepository()
    private val _aspectRatio = MutableStateFlow(AspectRatio.RatioCustom)
    val aspectRatio = _aspectRatio.asStateFlow()

    init {
        _aspectRatio.update { repository.fetchAspectRatio() }
    }
}