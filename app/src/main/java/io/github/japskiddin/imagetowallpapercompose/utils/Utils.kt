package io.github.japskiddin.imagetowallpapercompose.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.github.japskiddin.imagetowallpapercompose.AppTheme
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme


fun openFile(
    context: Context,
    openDocumentLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
    getContentLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    try {
        openDocumentLauncher.launch(listOf("image/*").toTypedArray())
    } catch (e: ActivityNotFoundException) {
        try {
            getContentLauncher.launch("image/*")
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context.applicationContext,
                R.string.err_activity_not_found,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

@Composable
fun PreviewWithTheme(content: @Composable () -> Unit) {
    val appTheme = if (isSystemInDarkTheme()) AppTheme.MODE_NIGHT else AppTheme.MODE_DAY
    ImageToWallpaperTheme(
        appTheme = appTheme,
        content = content
    )
}