package io.github.japskiddin.imagetowallpapercompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.japskiddin.imagetowallpapercompose.utils.DataStoreUtil

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDataStoreUtil(@ApplicationContext content: Context): DataStoreUtil =
        DataStoreUtil(content)
}