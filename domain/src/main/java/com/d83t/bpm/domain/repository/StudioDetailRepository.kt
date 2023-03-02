package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Studio
import kotlinx.coroutines.flow.Flow

interface StudioDetailRepository {

    suspend fun fetchStudioDetail(
        studioId: Int
    ) : Flow<ResponseState<Studio>>
}