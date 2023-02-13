package com.d83t.bpm.domain.repository

import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getStoredId(): Flow<String>
}