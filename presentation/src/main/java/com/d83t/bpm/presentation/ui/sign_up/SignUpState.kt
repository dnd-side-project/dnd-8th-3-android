package com.d83t.bpm.presentation.ui.sign_up

import com.d83t.bpm.domain.model.UserInfo
import com.d83t.bpm.presentation.util.ComposeUiState

interface SignUpState : ComposeUiState {
    object Init : SignUpState

    object Loading : SignUpState

    data class SignUpSuccess(val userInfo: UserInfo) : SignUpState

    object Error : SignUpState
}