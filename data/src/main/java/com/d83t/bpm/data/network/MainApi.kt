package com.d83t.bpm.data.network

import com.d83t.bpm.data.model.request.ScheduleRequest
import com.d83t.bpm.data.model.request.UserVerificationRequest
import com.d83t.bpm.data.model.response.ScheduleResponse
import com.d83t.bpm.data.model.response.SignUpResponse
import com.d83t.bpm.data.model.response.StudioResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MainApi {
    @Headers("shouldBeAuthorized: false")
    @Multipart
    @POST("api/users/signup")
    suspend fun signUp(
        @Part("kakaoId") kakaoId: Long,
        @Part("nickname") nickname: String,
        @Part("bio") bio: String,
        @Part file: MultipartBody.Part,
    ): Response<SignUpResponse>

    @GET("api/users/schedule")
    suspend fun fetchSchedule(): Response<ScheduleResponse>

    @POST("api/users/schedule")
    suspend fun sendSchedule(
        @Body schedule: ScheduleRequest
    ): Response<ScheduleResponse>

    @Headers("shouldBeAuthorized: false")
    @POST("api/users/verification")
    suspend fun sendKakaoUserIdVerification(
        @Body kakaoUserIdReq: UserVerificationRequest
    ): Response<SignUpResponse>

    @GET("api/studio/{studioId}")
    suspend fun fetchStudioDetail(
        @Path("studioId") studioId: Int
    ): Response<StudioResponse>
}