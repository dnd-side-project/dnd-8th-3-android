package com.d83t.bpm.presentation.ui.studio_detail

import com.d83t.bpm.domain.model.Studio

sealed interface StudioDetailState {
    object Init: StudioDetailState
    object Loading: StudioDetailState
    data class Success(val studio: Studio): StudioDetailState
    object Error: StudioDetailState
}