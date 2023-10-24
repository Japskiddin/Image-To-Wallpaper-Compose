package io.github.japskiddin.imagetowallpapercompose.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.japskiddin.imagetowallpapercompose.data.repository.SettingsRepository

class SettingsViewModel(repository: SettingsRepository) : ViewModel() {
    val appPreferences = repository.getFromDataStore()
}

class SettingsViewModelFactory(private val repository: SettingsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}