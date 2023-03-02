package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserInfo
import java.io.File
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun sendSignUp(
        kakaoId: Long,
        nickname: String,
        bio: String,
        image: File
    ): Flow<ResponseState<UserInfo>>
}