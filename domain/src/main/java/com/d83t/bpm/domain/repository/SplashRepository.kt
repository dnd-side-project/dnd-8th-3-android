package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getKakaoUserId(): Flow<Long?>

    suspend fun setKakaoUserId(kakaoUserId: Long): Flow<Long?>

    fun getUserToken(): Flow<String?>

    suspend fun setUserToken(userToken: String): Flow<String?>

    suspend fun sendSignIn(kakaoUserId: Long): Flow<ResponseState<UserInfo>>
}