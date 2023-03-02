package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.Schedule
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleResponse(
    @SerializedName(value = "studioName")
    val studioName: String,
    @SerializedName(value = "date")
    val date: String,
    @SerializedName(value = "time")
    val time: String,
    @SerializedName(value = "memo")
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