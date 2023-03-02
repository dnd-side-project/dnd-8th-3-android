package com.d83t.bpm.domain.usecase.studio_detail

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.domain.repository.StudioDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioDetailUseCase @Inject constructor(
    private val studioDetailRepository: StudioDetailRepository
) {
    suspend operator fun invoke(
        studioId: Int
    ): Flow<ResponseState<Studio>> {
        return studioDetailRepository.fetchStudioDetail(studioId = studioId)
    }
}