package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val studioName: String,
    val date: String,
    val time: String,
    val memo: String
) : BaseModel
