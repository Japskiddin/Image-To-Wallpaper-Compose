package io.github.japskiddin.imagetowallpapercompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

enum class AspectRatio(val width: Int, val height: Int) {
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
    MODE_AUTO;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}

data class AppPreferences(val aspectRatio: AspectRatio, val appTheme: AppTheme)

val DEFAULT_PREFERENCE: AppPreferences = AppPreferences(AspectRatio.RATIO_4_TO_3, AppTheme.MODE_DAY)

const val APP_DATASTORE = "app_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

class SettingsRepository(private val context: Context) {
    companion object {
        val ASPECT_RATIO = intPreferencesKey("ASPECT_RATIO")
        val NIGHT_MODE = intPreferencesKey("NIGHT_MODE")
    }

    suspend fun saveToDataStore(appPreferences: AppPreferences) {
        context.dataStore.edit {
            it[ASPECT_RATIO] = appPreferences.aspectRatio.ordinal
            it[NIGHT_MODE] = appPreferences.appTheme.ordinal
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        AppPreferences(
            aspectRatio = AspectRatio.fromOrdinal(
                it[ASPECT_RATIO] ?: AspectRatio.RATIO_4_TO_3.ordinal
            ),
            appTheme = AppTheme.fromOrdinal(it[NIGHT_MODE] ?: AppTheme.MODE_DAY.ordinal)
        )
    }
}