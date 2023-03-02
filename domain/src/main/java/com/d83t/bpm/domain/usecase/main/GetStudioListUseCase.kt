package com.d83t.bpm.domain.usecase.main

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.StudioList
import com.d83t.bpm.domain.repository.MainRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@Reusable
class GetStudioListUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): Flow<ResponseState<StudioList>> {
        return mainRepository.getStudioList(limit, offset)
    }
}