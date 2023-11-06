package io.github.japskiddin.imagetowallpapercompose.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.IOException
import java.io.InputStream


fun getBitmapFromUri(context: Context, uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {
    var input: InputStream? = null
    return try {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        input = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(input, null, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        input = context.contentResolver.openInputStream(uri)
        val result = BitmapFactory.decodeStream(input, null, options)
        val degree = readPictureDegree(context, uri)
        if (degree > 0f) rotateBitmap(degree, result) else result
    } catch (e: OutOfMemoryError) {
        getBitmapFromUri(context, uri, reqWidth / 2, reqHeight / 2)
    } catch (e: Exception) {
        null
    } finally {
        try {
            input?.close()
        } catch (ignored: IOException) {
        }
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight
            && halfWidth / inSampleSize >= reqWidth
        ) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun readPictureDegree(context: Context, uri: Uri): Float {
    var input: InputStream? = null
    return try {
        input = context.contentResolver.openInputStream(uri)
        if (input != null) {
            val exifInterface = ExifInterface(input)
            checkEditInterface(exifInterface)
        } else {
            0f
        }
    } catch (e: IOException) {
        0f
    } finally {
        try {
            input?.close()
        } catch (ignored: IOException) {
        }
    }
}

private fun checkEditInterface(exifInterface: ExifInterface): Float {
    val orientation: Int = exifInterface.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        else -> 0f
    }
}

fun rotateBitmap(degree: Float, bitmap: Bitmap?): Bitmap? {
    var resultBitmap: Bitmap? = null
    val matrix = Matrix().apply {
        postRotate(degree)
    }
    try {
        resultBitmap = bitmap?.let {
            Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)
        }
    } catch (ignored: OutOfMemoryError) {
    }
    if (resultBitmap == null) {
        resultBitmap = bitmap
    }
    return resultBitmap
}