package com.d83t.bpm.data.model.response

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.data.mapper.DataMapper
import com.d83t.bpm.data.model.response.StudioResponse.Companion.toDataModel
import com.d83t.bpm.domain.model.StudioList
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudioListResponse(
    @SerializedName("studios")
    val studios: List<StudioResponse>?,
    @SerializedName("studioCount")
    val studioCount: Int?,
) : BaseResponse {
    companion object : DataMapper<StudioListResponse, StudioList> {
        override fun StudioListResponse.toDataModel(): StudioList {
            return StudioList(
                studios = studios?.map { it.toDataModel() } ?: emptyList(),
                studioCount = studioCount
            )
        }
    }
}