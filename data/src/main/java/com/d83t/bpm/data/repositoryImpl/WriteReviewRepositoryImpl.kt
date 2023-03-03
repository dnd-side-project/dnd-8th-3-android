package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.response.ReviewResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.data.util.createImageMultipartBody
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.repository.WriteReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

class WriteReviewRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : WriteReviewRepository {
    override suspend fun sendReview(
        studioId: Int,
        images: List<File>,
        rating: Double,
        recommends: List<String>,
        content: String
    ): Flow<ResponseState<Review>> {

        return flow {
            BPMResponseHandler().handle {
                mainApi.sendReview(
                    studioId = 1,
                    files = images.map {
                        createImageMultipartBody(
                            key = "files",
                            file = it
                        )
                    },
                    rating = rating,
                    recommends = recommends,
                    content = content
                )
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.toDataModel()))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }
}