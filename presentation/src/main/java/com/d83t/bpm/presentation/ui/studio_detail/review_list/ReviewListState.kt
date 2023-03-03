package com.d83t.bpm.presentation.ui.studio_detail.review_list

import com.d83t.bpm.domain.model.Review

sealed interface ReviewListState {
    object Init: ReviewListState
    data class Success(val reviewList: List<Review>): ReviewListState
    object Error: ReviewListState
}