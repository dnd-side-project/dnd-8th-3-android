package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.Schedule
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleResponse(
    val studioName: String,
    val date: String,
    val time: String,
    val memo: String
) : BaseResponse {

    companion object : DataMapper<ScheduleResponse, Schedule> {
        override fun ScheduleResponse.toDataModel(): Schedule {
            return Schedule(
                studioName = studioName,
                date = date,
                time = time,
                memo = memo
            )
        }
    }
}