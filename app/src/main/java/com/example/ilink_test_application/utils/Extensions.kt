package com.example.ilink_test_application.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

fun Drawable.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}