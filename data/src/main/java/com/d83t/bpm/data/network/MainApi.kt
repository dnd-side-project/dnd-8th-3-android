package com.d83t.bpm.data.network

import com.d83t.bpm.data.model.request.ScheduleRequest
import com.d83t.bpm.data.model.response.ScheduleResponse
import com.d83t.bpm.data.model.response.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MainApi {
    @Multipart
    @POST("api/users/signup")
    suspend fun signUp(
        @Part("kakaoId") kakaoId: Long,
        @Part("nickname") nickname: String,
        @Part("bio") bio: String,
        @Part file: MultipartBody.Part,
    ): Response<SignUpResponse>

    @POST("api/users/schedule")
    suspend fun sendSchedule(
        @Body schedule: ScheduleRequest
    ): Response<ScheduleResponse>
}