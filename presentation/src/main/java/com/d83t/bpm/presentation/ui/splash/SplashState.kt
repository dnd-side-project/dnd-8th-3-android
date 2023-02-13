package com.d83t.bpm.presentation.ui.splash

import com.d83t.bpm.presentation.util.ComposeUiState

sealed interface SplashState : ComposeUiState {
    object Init : SplashState
    data class KakaoId(val id: String) : SplashState
}