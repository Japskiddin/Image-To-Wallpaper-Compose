package io.github.japskiddin.imagetowallpapercompose

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil.Companion.KEY_CROP_RATIO
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil.Companion.KEY_THEME
import io.moyuru.cropify.AspectRatio
import io.moyuru.cropify.CropifyOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WallpaperType {
    HOME,
    LOCK,
    BOTH
}

enum class CropRatio(val width: Int, val height: Int) {
    RATIO_4_TO_3(width = 4, height = 3),
    RATIO_3_TO_4(width = 3, height = 4),
    RATIO_16_TO_9(width = 16, height = 9),
    RATIO_9_TO_16(width = 9, height = 16),
    RATIO_18_TO_9(width = 18, height = 9),
    RATIO_9_TO_18(width = 9, height = 18),
    RATIO_CUSTOM(width = 0, height = 0);

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }

    override fun toString(): String {
        return "$width:$height"
    }
}

enum class AppTheme {
    MODE_DAY,
    MODE_NIGHT,
    MODE_SYSTEM;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}

data class SettingsState(
    val theme: AppTheme = AppTheme.MODE_SYSTEM,
    val cropRatio: CropRatio = CropRatio.RATIO_4_TO_3
)

@HiltViewModel
class AppViewModel @Inject constructor(dataStoreUtil: DataStoreUtil) : ViewModel() {
    private val dataStore = dataStoreUtil.dataStore

    private val _settingsState = MutableStateFlow(SettingsState())
    private val _imageUri = MutableStateFlow<Uri?>(null)
    private val _cropifyOption = MutableStateFlow(
        CropifyOption(backgroundColor = Color.Transparent)
    )
    val settingsState: StateFlow<SettingsState> = _settingsState
    val imageUri: StateFlow<Uri?> = _imageUri
    val cropifyOption: StateFlow<CropifyOption> = _cropifyOption

    init {
        fetchSettings()
    }

    fun setAppTheme(appTheme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[KEY_THEME] = appTheme.ordinal
            }
            _settingsState.update {
                it.copy(theme = appTheme)
            }
        }
    }

    fun setCropRatio(cropRatio: CropRatio) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[KEY_CROP_RATIO] = cropRatio.ordinal
            }
            _settingsState.update {
                it.copy(cropRatio = cropRatio)
            }
            setCropifyOptionRatio()
        }
    }

    fun setImageUri(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _imageUri.update { uri }
        }
    }

    fun setCropifyOptionBackground(color: Color) {
        viewModelScope.launch(Dispatchers.IO) {
            _cropifyOption.update {
                it.copy(maskColor = color)
            }
        }
    }

    private fun setCropifyOptionRatio() {
        viewModelScope.launch(Dispatchers.IO) {
            val cropRatio = _settingsState.value.cropRatio
            _cropifyOption.update {
                it.copy(
                    frameAspectRatio = if (cropRatio == CropRatio.RATIO_CUSTOM) {
                        null
                    } else {
                        AspectRatio(cropRatio.width, cropRatio.height)
                    }
                )
            }
        }
    }

    private fun fetchSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                SettingsState(
                    theme = AppTheme.fromOrdinal(
                        preferences[KEY_THEME] ?: AppTheme.MODE_DAY.ordinal
                    ),
                    cropRatio = CropRatio.fromOrdinal(
                        preferences[KEY_CROP_RATIO] ?: CropRatio.RATIO_4_TO_3.ordinal
                    )
                )
            }.collect {
                _settingsState.value = it
                setCropifyOptionRatio()
            }
        }
    }
}