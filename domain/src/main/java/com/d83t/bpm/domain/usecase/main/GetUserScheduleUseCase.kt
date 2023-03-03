package com.d83t.bpm.domain.usecase.main

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserSchedule
import com.d83t.bpm.domain.repository.MainRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@Reusable
class GetUserScheduleUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): Flow<ResponseState<UserSchedule>> {
        return mainRepository.getUserSchedule()
    }
}