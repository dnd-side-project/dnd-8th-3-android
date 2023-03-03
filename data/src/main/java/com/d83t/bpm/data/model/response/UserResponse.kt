package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName(value = "id")
    val id: Int?,
    @SerializedName(value = "nickname")
    val nickname: String?,
    @SerializedName(value = "profilePath")
    val profilePath: String?
) : BaseResponse {

    companion object : DataMapper<UserResponse, User> {
        override fun UserResponse.toDataModel(): User {
            return User(
                id = id?: 0,
                nickname = nickname ?: "",
                profilePath = profilePath ?: ""
            )
        }
    }
}
