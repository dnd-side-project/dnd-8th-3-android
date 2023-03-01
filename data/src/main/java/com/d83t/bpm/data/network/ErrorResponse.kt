package com.d83t.bpm.data.network

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.NetworkError
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    val timeStamp: String? = null,
    val status: String? = null,
    val error: String? = null,
    val code: String? = null,
    val message: String? = null
) : BaseResponse {
    companion object : DataMapper<ErrorResponse, NetworkError> {
        override fun ErrorResponse.toDataModel(): NetworkError {
            return NetworkError(
                error = error ?: "null",
                code = code ?: "null",
                message = message ?: "Unknown Error"
            )
        }
    }
}
