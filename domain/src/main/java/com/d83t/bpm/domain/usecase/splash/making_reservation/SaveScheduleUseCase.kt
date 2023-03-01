package com.d83t.bpm.domain.usecase.splash.making_reservation

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Schedule
import com.d83t.bpm.domain.repository.MakingReservationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveScheduleUseCase @Inject constructor(
    private val makingReservationRepository: MakingReservationRepository
) {

    suspend operator fun invoke(
        studioName: String,
        date: String,
        time: String,
        memo: String
    ): Flow<ResponseState<Schedule>> {
        return makingReservationRepository.sendSchedule(
            studioName = studioName,
            date = date,
            time = time,
            memo = memo
        )
    }
}