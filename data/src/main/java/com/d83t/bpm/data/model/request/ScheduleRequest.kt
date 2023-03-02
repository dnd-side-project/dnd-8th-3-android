package com.d83t.bpm.data.model.request

import com.google.gson.annotations.SerializedName

data class ScheduleRequest(
    @SerializedName(value = "studioName")
    val studioName: String,
    @SerializedName(value = "date")
    val date: String,
    @SerializedName(value = "time")
    val time: String,
    @SerializedName(value = "memo")
    val memo: String
)
