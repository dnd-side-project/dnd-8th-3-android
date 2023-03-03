package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: Int?,
    val studio: Studio?,
    val author: User?,
    val rating: Double?,
    val recommends: List<String>?,
    val filesPath: List<String>?,
    val content: String?,
    val likeCount: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val liked: Boolean?
) : BaseModel