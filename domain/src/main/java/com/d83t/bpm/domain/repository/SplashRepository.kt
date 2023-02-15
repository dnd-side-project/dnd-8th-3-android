package com.d83t.bpm.domain.repository

import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getKakaoUserId(): Flow<String?>

    suspend fun setKakaoUserId(kakaoId : String): Flow<String?>
}