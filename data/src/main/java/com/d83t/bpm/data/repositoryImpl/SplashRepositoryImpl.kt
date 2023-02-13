package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.datastore.DataStoreManager
import com.d83t.bpm.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : SplashRepository {

    override fun getStoredId(): Flow<String> {
        return dataStoreManager.getKakaoId()
    }
}