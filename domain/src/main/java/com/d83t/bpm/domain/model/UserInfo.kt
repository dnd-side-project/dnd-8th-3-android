package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val nickname: String,
    val bio: String,
    val token: String,
    val image: String
) : BaseModel
