package io.github.japskiddin.imagetowallpapercompose.utils

import io.github.japskiddin.imagetowallpapercompose.data.model.AspectRatio
import io.github.japskiddin.imagetowallpapercompose.data.model.RATIO_4_TO_3

fun getAspectRatioFromString(value: String): AspectRatio {
    return try {
        val items = value.split(":")
        AspectRatio(width = items[0].toInt(), height = items[1].toInt())
    } catch (e: Exception) {
        getAspectRatioFromString(RATIO_4_TO_3)
    }
}