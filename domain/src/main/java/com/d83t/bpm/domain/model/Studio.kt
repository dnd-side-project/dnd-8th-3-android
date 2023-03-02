package com.d83t.bpm.domain.model

import com.d83t.bpm.domain.base.BaseModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Studio(
    @SerializedName(value = "id")
    val id: Int?,
    @SerializedName(value = "name")
    val name: String?,
    @SerializedName(value = "address")
    val address: String?,
    @SerializedName(value = "latitude")
    val latitude: Double?,
    @SerializedName(value = "longitude")
    val longitude: Double?,
    @SerializedName(value = "firstTag")
    val firstTag: String?,
    @SerializedName(value = "secondTag")
    val secondTag: String?,
    @SerializedName(value = "phone")
    val phone: String?,
    @SerializedName(value = "sns")
    val sns: String?,
    @SerializedName(value = "openHours")
    val openHours: String?,
    @SerializedName(value = "price")
    val price: String?,
    @SerializedName(value = "filesPath")
    val filesPath: List<String>?,
    @SerializedName(value = "content")
    val content: String?,
    @SerializedName(value = "rating")
    val rating: Double?,
    @SerializedName(value = "reviewCount")
    val reviewCount: Int?,
    @SerializedName(value = "scrapCount")
    val scrapCount: Int?,
    @SerializedName(value = "createdAt")
    val createdAt: String?,
    @SerializedName(value = "updatedAt")
    val updatedAt: String?
) : BaseModel