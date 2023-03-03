package com.d83t.bpm.presentation.ui.studio_detail

import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.model.Studio

sealed interface StudioDetailState {
    object Init: StudioDetailState
    object Loading: StudioDetailState
    data class StudioDetailSuccess(val studio: Studio): StudioDetailState
    data class ReviewListSuccess(val reviewList: List<Review>) : StudioDetailState
    object Error: StudioDetailState
}