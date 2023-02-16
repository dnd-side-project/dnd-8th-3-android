package com.d83t.bpm.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DataStoreManager @Inject constructor(private val context: Context) {
    private val Context.instance: DataStore<Preferences> by preferencesDataStore(KEY_DATASTORE)

    fun getKakaoUserId(): Flow<String?> {
        return context.instance.data.map { preferences ->
            preferences[stringPreferencesKey(KEY_KAKAO_USER_ID)]
        }
    }

    suspend fun setKakaoUserId(kakaoUserId: String): Flow<String?> {
        return flow {
            context.instance.edit { preferences ->
                preferences[stringPreferencesKey(KEY_KAKAO_USER_ID)] = kakaoUserId
            }
        }
    }

    companion object {
        private const val KEY_DATASTORE = "bpm"
        private const val KEY_KAKAO_USER_ID = "kakao_user_id"
    }
}