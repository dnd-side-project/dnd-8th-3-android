package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkError(
    val error: String,
    val code: String,
    val message: String
) : BaseModel
