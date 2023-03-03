package com.d83t.bpm.presentation.util

fun String.dateOnly(): String {
    return this.substring(0..9).replace("-", ".")
}

fun Double.clip(): String {
    return this.toString().substring(0..2)
}