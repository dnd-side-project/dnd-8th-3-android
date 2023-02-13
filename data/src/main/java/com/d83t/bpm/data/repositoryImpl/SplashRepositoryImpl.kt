package com.d83t.bpm.data.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.d83t.bpm.data.datastore.KAKAO_ID
import com.d83t.bpm.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SplashRepository {

    override fun getStoredId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[KAKAO_ID] ?: "null"
        }
    }
}