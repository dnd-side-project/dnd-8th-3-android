package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.UserInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    val nickname: String,
    val bio: String,
    val token: String,
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
