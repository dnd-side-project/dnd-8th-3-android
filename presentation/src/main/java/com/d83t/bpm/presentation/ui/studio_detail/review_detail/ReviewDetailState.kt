package com.d83t.bpm.presentation.ui.studio_detail.review_detail

import com.d83t.bpm.domain.model.Review

sealed interface ReviewDetailState {
    object Init : ReviewDetailState
    data class Success(val review: Review) : ReviewDetailState
    object Error : ReviewDetailState
}