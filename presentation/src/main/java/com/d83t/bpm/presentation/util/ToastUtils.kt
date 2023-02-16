package com.d83t.bpm.presentation.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showDebugToast(text: String) {
    Toast.makeText(this, "Debug Mode! $text", Toast.LENGTH_SHORT).show()
}