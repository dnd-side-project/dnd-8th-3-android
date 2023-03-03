package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.request.ReviewRequest
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.repository.WriteReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                    studioId = studioId,
                    files = images, // TODO : to Multipart
                    reviewRequest = ReviewRequest(
                        rating = rating,
                        recommends = recommends,
                        content = content
                    )
                )
            }
        }
    }
}