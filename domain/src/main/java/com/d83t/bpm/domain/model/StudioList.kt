package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudioList(
    val studios: List<Studio>?,
    val studioCount: Int?,
) : BaseModel