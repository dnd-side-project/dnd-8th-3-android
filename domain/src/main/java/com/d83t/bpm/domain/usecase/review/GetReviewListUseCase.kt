package com.d83t.bpm.domain.usecase.review

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewListUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(studioId: Int): Flow<ResponseState<List<Review>>> {
        return reviewRepository.fetchReviewList(studioId = studioId)
    }
}