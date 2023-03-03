package com.d83t.bpm.presentation.ui.main.home.recommend

sealed interface HomeRecommendState {
    object Init : HomeRecommendState
    object List : HomeRecommendState

    object Error : HomeRecommendState
}