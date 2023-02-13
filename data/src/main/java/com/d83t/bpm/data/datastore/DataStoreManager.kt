package com.d83t.bpm.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {
    private val Context.instance: DataStore<Preferences> by preferencesDataStore(name = "bpm")

    fun getKakaoId(): Flow<String> {
        return context.instance.data.map { preferences ->
            preferences[stringPreferencesKey(name = "kakaoId")] ?: "null"
        }
    }
}