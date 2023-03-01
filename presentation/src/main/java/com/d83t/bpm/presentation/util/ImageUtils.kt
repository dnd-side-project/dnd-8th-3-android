package com.d83t.bpm.presentation.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.File
import java.io.FileOutputStream

fun convertUriToBitmap(
    contentResolver: ContentResolver,
    uri: Uri,
): Bitmap {
    return ImageDecoder.decodeBitmap(
        ImageDecoder.createSource(
            contentResolver,
            uri
        )
    )
}

fun convertBitmapToWebpFile(bitmap: ImageBitmap): File {
    val webpFile = File.createTempFile(
        "IMG_",
        ".webp"
    )
    val fileOutputStream = FileOutputStream(webpFile)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        bitmap
            .asAndroidBitmap()
            .compress(
                Bitmap.CompressFormat.WEBP_LOSSY,
                50,
                fileOutputStream
            )

        fileOutputStream.close()
    }

    return webpFile
}
