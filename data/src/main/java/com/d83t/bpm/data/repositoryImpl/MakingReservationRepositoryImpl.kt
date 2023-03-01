package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.request.ScheduleRequest
import com.d83t.bpm.data.model.response.ScheduleResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Schedule
import com.d83t.bpm.domain.repository.MakingReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MakingReservationRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : MakingReservationRepository {

    override suspend fun sendSchedule(
        studioName: String,
        date: String,
        time: String,
        memo: String
    ): Flow<ResponseState<Schedule>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.sendSchedule(
                    schedule = ScheduleRequest(
                        studioName = studioName,
                        date = date,
                        time = time,
                        memo = memo
                    )
                )
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.toDataModel()))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }
}