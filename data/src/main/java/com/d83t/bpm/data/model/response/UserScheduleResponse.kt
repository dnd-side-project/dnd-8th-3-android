package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.domain.model.UserSchedule
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserScheduleResponse(
    @SerializedName("studioName")
    val studioName: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("memo")
    val memo: String?,
) : BaseResponse {
    companion object : DataMapper<UserScheduleResponse, UserSchedule> {
        override fun UserScheduleResponse.toDataModel(): UserSchedule {
            return UserSchedule(
                studioName = studioName,
                date = date,
                time = time,
                memo = memo
            )
        }
    }
}