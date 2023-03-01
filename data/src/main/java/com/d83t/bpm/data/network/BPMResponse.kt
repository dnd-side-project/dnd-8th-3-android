package com.d83t.bpm.data.network

sealed class BPMResponse<out T> {
    data class Success<T>(val data: T) : BPMResponse<T>()
    data class Error(val error: ErrorResponse) : BPMResponse<Nothing>()
}
