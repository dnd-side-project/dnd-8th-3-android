package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.response.ReviewResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : ReviewRepository {
    override suspend fun fetchReviewList(
        studioId: Int
    ): Flow<ResponseState<List<Review>>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.fetchReviewList(studioId = studioId)
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.map { it.toDataModel() }))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }

    override suspend fun fetchReviewDetail(
        studioId: Int,
        reviewId: Int
    ): Flow<ResponseState<Review>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.fetchReviewDetail(
                    studioId = studioId,
                    reviewId = reviewId
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