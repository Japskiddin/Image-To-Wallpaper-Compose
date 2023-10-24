package io.github.japskiddin.imagetowallpapercompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.japskiddin.imagetowallpapercompose.data.model.AspectRatio
import io.github.japskiddin.imagetowallpapercompose.data.model.RATIO_4_TO_3
import io.github.japskiddin.imagetowallpapercompose.utils.getAspectRatioFromString
import kotlinx.coroutines.flow.map

enum class AppTheme {
    MODE_DAY,
    MODE_NIGHT,
    MODE_AUTO;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}

data class AppPreferences(val aspectRatio: AspectRatio, val appTheme: AppTheme)

val DEFAULT_PREFERENCE: AppPreferences = AppPreferences(AspectRatio(4, 3), AppTheme.MODE_DAY)

const val APP_DATASTORE = "app_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

class SettingsRepository(private val context: Context) {
    companion object {
        val ASPECT_RATIO = stringPreferencesKey("ASPECT_RATIO")
        val NIGHT_MODE = intPreferencesKey("NIGHT_MODE")
    }

    suspend fun saveToDataStore(appPreferences: AppPreferences) {
        context.dataStore.edit {
            it[ASPECT_RATIO] = appPreferences.aspectRatio.toString()
            it[NIGHT_MODE] = appPreferences.appTheme.ordinal
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        AppPreferences(
            aspectRatio = getAspectRatioFromString(it[ASPECT_RATIO] ?: RATIO_4_TO_3),
            appTheme = AppTheme.fromOrdinal(it[NIGHT_MODE] ?: AppTheme.MODE_DAY.ordinal)
        )
    }
}