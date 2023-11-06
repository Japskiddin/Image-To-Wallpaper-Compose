package io.github.japskiddin.imagetowallpapercompose.utils

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import io.github.japskiddin.imagetowallpapercompose.R

fun hasPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun requestPermission(context: Context, launcher: ManagedActivityResultLauncher<String, Boolean>) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    try {
        launcher.launch(permission)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            context.applicationContext,
            R.string.err_activity_not_found,
            Toast.LENGTH_LONG
        ).show()
    }
}