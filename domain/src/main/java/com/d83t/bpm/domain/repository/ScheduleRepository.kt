package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun sendSchedule(
        studioName: String,
        date: String,
        time: String,
        memo: String
    ): Flow<ResponseState<Schedule>>
}