package com.d83t.bpm.domain.repository

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import kotlinx.coroutines.flow.Flow
import java.io.File

interface WriteReviewRepository {
    suspend fun sendReview(
        studioId: Int,
        images: List<File>,
        rating: Double,
        recommends: List<String>,
        content: String
    ): Flow<ResponseState<Review>>
}