package com.d83t.bpm.data.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun createImageMultipartBody(key: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name = key,
        filename = file.name,
        body = file.asRequestBody("image/*".toMediaType())
    )
}