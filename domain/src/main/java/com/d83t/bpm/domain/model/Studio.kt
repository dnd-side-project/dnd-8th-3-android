package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import kotlin.math.round
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Studio(
    val id: Int?,
    val name: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val firstTag: String?,
    val secondTag: String?,
    val phone: String?,
    val sns: String?,
    val openHours: String?,
    val price: String?,
    val filesPath: List<String>?,
    val content: String?,
    val rating: Double?,
    val reviewCount: Int?,
    val scrapCount: Int?,
    val createdAt: String?,
    val updatedAt: String?
) : BaseModel {

    // TODO : 죄송합니다..

    @IgnoredOnParcel
    val tagList = listOf<String>(
        "친절해요", "소통이 빨라요", "깨끗해요"
    )

    @IgnoredOnParcel
    val ratingText: String = "${round((rating?.times(10) ?: 0) as Double) / 10}"

}