package com.d83t.bpm.data.network

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class BPMResponseHandler {
    suspend fun <T> handle(request: suspend () -> Response<T>): Flow<BPMResponse<T>> {
        return flow {
            val response = request.invoke()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(BPMResponse.Success(data))
                } else {
                    emit(BPMResponse.Error(ErrorResponse(message = "Unknown ErrorOccurred.")))
                }
            } else {
                try {
                    emit(BPMResponse.Error(Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)))
                } catch (e: Exception) {
                    emit(BPMResponse.Error(ErrorResponse(message = "Unknown ErrorOccurred.")))
                }
            }
        }
    }
}