package io.github.japskiddin.imagetowallpapercompose.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class DataStoreUtil @Inject constructor(context: Context) {
    val dataStore = context.dataStore

    companion object {
        private const val APP_DATARSTORE = "app_datastore"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_DATARSTORE)

        val KEY_THEME = intPreferencesKey("theme")
        val KEY_CROP_RATIO = intPreferencesKey("crop_ratio")
    }
}