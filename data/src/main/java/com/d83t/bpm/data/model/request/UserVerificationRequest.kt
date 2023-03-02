package com.d83t.bpm.data.model.request

import com.google.gson.annotations.SerializedName

data class UserVerificationRequest(
    @SerializedName("kakaoId")
    val kakaoUserId: Long
)
