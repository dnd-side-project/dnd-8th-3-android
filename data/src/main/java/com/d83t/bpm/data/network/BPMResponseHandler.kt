package com.d83t.bpm.data.network

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class BPMResponseHandler {
    suspend fun <T> handle(call: suspend () -> Response<T>): Flow<BPMResponse<T>> {
        return flow {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                emit(BPMResponse.Success(response.body()!!))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = if (errorBody.isNullOrEmpty()) {
                    response.message()
                } else {
                    errorBody
                }
                emit(BPMResponse.Error(ErrorResponse(code = response.code().toString(), message = message ?: "Unknown Error")))
            }
        }
    }
}