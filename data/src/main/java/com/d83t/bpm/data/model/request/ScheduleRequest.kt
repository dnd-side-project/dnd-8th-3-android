package com.d83t.bpm.data.model.request

data class ScheduleRequest(
    val studioName: String,
    val date: String,
    val time: String,
    val memo: String
)
