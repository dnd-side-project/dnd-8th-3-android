package com.d83t.bpm.presentation.ui.studio_detail.writing_review

import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.domain.model.Studio

sealed interface WritingReviewState {
    object Init : WritingReviewState
    object Loading : WritingReviewState
    data class StudioSuccess(val studio: Studio) : WritingReviewState
    data class ReviewSuccess(val review: Review) : WritingReviewState
    object Error : WritingReviewState
}