package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.domain.model.StudioList
import kotlinx.coroutines.flow.Flow

interface SearchStudioRepository {
    suspend fun fetchSearchStudioResult(
        query: String
    ) : Flow<ResponseState<StudioList>>
}