package io.github.japskiddin.imagetowallpapercompose.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import io.github.japskiddin.imagetowallpapercompose.R


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