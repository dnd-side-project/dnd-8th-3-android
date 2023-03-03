package com.d83t.bpm.domain.usecase.splash

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.repository.WriteReviewRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class WriteReviewUseCase @Inject constructor(
    private val writeReviewRepository: WriteReviewRepository
) {
    suspend operator fun invoke(
        studioId: Int,
        images: List<File>,
        rating: Double,
        recommends: List<String>,
        content: String
    ): Flow<ResponseState<Review>> {
        return writeReviewRepository.sendReview(
            studioId = studioId,
            images = images,
            rating = rating,
            recommends = recommends,
            content = content
        )
    }
}