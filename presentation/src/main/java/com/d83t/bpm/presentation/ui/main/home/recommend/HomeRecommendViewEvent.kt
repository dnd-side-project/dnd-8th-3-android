package com.d83t.bpm.presentation.ui.main.home.recommend

interface HomeRecommendViewEvent {
    data class ClickDetail(val studioId : Int?) : HomeRecommendViewEvent
}