package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun fetchReviewList(studioId: Int): Flow<ResponseState<List<Review>>>

    suspend fun fetchReviewDetail(
        studioId: Int,
        reviewId: Int
    ): Flow<ResponseState<Review>>
}