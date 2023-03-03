package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.data.model.response.StudioResponse.Companion.toDataModel
import com.d83t.bpm.data.model.response.UserResponse.Companion.toDataModel
import com.d83t.bpm.domain.model.Review
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewResponse(
    @SerializedName(value = "id")
    val id: Int?,
    @SerializedName(value = "studio")
    val studio: StudioResponse?,
    @SerializedName(value = "author")
    val author: UserResponse?,
    @SerializedName(value = "rating")
    val rating: Double?,
    @SerializedName(value = "recommends")
    val recommends: List<String>?,
    @SerializedName(value = "filesPath")
    val filesPath: List<String>?,
    @SerializedName(value = "content")
    val content: String,
    @SerializedName(value = "likeCount")
    val likeCount: Int?,
    @SerializedName(value = "createdAt")
    val createdAt: String?,
    @SerializedName(value = "updatedAt")
    val updatedAt: String?,
    @SerializedName(value = "liked")
    val liked: Boolean?
) : BaseResponse {
    companion object : DataMapper<ReviewResponse, Review> {
        override fun ReviewResponse.toDataModel(): Review {
            return Review(
                id = id,
                studio = studio?.toDataModel(),
                author = author?.toDataModel(),
                rating = rating,
                recommends = recommends,
                filesPath = filesPath,
                content = content,
                likeCount = likeCount,
                createdAt = createdAt,
                updatedAt = updatedAt,
                liked = liked
            )
        }
    }
}