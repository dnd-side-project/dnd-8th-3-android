package com.d83t.bpm.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DataStoreManager @Inject constructor(private val context: Context) {
    private val Context.instance: DataStore<Preferences> by preferencesDataStore(KEY_DATASTORE)

    fun getKakaoUserId(): Flow<Long?> {
        return context.instance.data.map { preferences ->
            preferences[longPreferencesKey(KEY_KAKAO_USER_ID)]
        }
    }

    suspend fun setKakaoUserId(kakaoUserId: Long): Flow<Long?> {
        context.instance.edit { preferences ->
            preferences[longPreferencesKey(KEY_KAKAO_USER_ID)] = kakaoUserId
        }

        return getKakaoUserId()
    }

    fun getUserToken(): Flow<String?> {
        return context.instance.data.map { preferences ->
            preferences[stringPreferencesKey(KEY_USER_TOKEN)]
        }
    }

    suspend fun setUserToken(token: String): Flow<String?> {
        return flow {
            context.instance.edit { preferences ->
                preferences[stringPreferencesKey(KEY_USER_TOKEN)] = token
            }
        }
    }


    companion object {
        private const val KEY_DATASTORE = "bpm"
        private const val KEY_KAKAO_USER_ID = "kakao_user_id"
        private const val KEY_USER_TOKEN = "user_token"
    }
}