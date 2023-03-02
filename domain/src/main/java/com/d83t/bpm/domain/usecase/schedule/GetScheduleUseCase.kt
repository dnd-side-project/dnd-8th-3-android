package com.d83t.bpm.domain.usecase.schedule

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Schedule
import com.d83t.bpm.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {

    suspend operator fun invoke(): Flow<ResponseState<Schedule>> {
        return scheduleRepository.fetchSchedule()
    }
}