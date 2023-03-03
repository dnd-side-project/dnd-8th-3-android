package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.StudioList
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getStudioList(limit: Int, offset: Int): Flow<ResponseState<StudioList>>

}