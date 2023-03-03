package com.d83t.bpm.presentation.util

fun String.dateOnly(): String {
    return this.dropLast(10).replace("-", ".")
}