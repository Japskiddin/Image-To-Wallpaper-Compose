package io.github.japskiddin.imagetowallpapercompose

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil.Companion.KEY_CROP_RATIO
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil.Companion.KEY_THEME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

data class ThemeState(val theme: AppTheme)

data class AspectRatioState(val cropRatio: CropRatio)

@HiltViewModel
class SettingsViewModel @Inject constructor(dataStoreUtil: DataStoreUtil) : ViewModel() {
    private val _themeState = MutableStateFlow(ThemeState(AppTheme.MODE_DAY))
    private val _cropRatioState = MutableStateFlow(AspectRatioState(CropRatio.RATIO_4_TO_3))
    val themeState: StateFlow<ThemeState> = _themeState
    val aspectRatioState: StateFlow<AspectRatioState> = _cropRatioState

    private val dataStore = dataStoreUtil.dataStore

    init {
        getAppTheme()
        getCropRatio()
    }

    private fun getCropRatio() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                AspectRatioState(
                    CropRatio.fromOrdinal(
                        preferences[KEY_CROP_RATIO] ?: CropRatio.RATIO_4_TO_3.ordinal
                    )
                )
            }.collect {
                _cropRatioState.value = it
            }
        }
    }

    private fun getAppTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(
                    AppTheme.fromOrdinal(
                        preferences[KEY_THEME] ?: AppTheme.MODE_DAY.ordinal
                    )
                )
            }.collect {
                _themeState.value = it
            }
        }
    }

    fun setAppTheme(appTheme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[KEY_THEME] = appTheme.ordinal
            }
            _themeState.update {
                it.copy(theme = appTheme)
            }
        }
    }

    fun setAspectRatio(cropRatio: CropRatio) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[KEY_CROP_RATIO] = cropRatio.ordinal
            }
            _cropRatioState.update {
                it.copy(cropRatio = cropRatio)
            }
        }
    }
}