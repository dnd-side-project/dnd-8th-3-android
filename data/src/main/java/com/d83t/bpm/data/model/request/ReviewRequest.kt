package com.d83t.bpm.data.model.request

import com.google.gson.annotations.SerializedName

data class ReviewRequest(
    @SerializedName(value = "rating")
    val rating: Double,
    @SerializedName(value = "recommends")
    val recommends: List<String>,
    @SerializedName(value = "content")
    val content: String
)