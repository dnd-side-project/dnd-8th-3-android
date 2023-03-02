package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.UserInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    @SerializedName(value = "nickname")
    val nickname: String,
    @SerializedName(value = "bio")
    val bio: String,
    @SerializedName(value = "token")
    val token: String,
    @SerializedName(value = "image")
    val image: String
) : BaseResponse {

    companion object : DataMapper<SignUpResponse, UserInfo> {
        override fun SignUpResponse.toDataModel(): UserInfo {
            return UserInfo(
                nickname = nickname,
                bio = bio,
                token = token,
                image = image
            )
        }
    }
}
