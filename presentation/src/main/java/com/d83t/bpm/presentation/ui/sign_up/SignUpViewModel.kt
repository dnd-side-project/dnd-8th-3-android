package com.d83t.bpm.presentation.ui.sign_up

import android.os.Bundle
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.sign_up.SignUpUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import com.d83t.bpm.presentation.util.convertBitmapToWebpFile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _event = MutableSharedFlow<SignUpViewEvent>()
    val event: SharedFlow<SignUpViewEvent>
        get() = _event

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Init)
    val state: StateFlow<SignUpState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    private fun getBundle(): Bundle? {
        return savedStateHandle.get<Bundle>(SignUpActivity.KEY_BUNDLE)
    }

    val kakaoUserInfo: Pair<Long, String> by lazy {
        Pair(
            getBundle()?.getLong(SignUpActivity.KEY_KAKAO_USER_ID) ?: 0L,
            getBundle()?.getString(SignUpActivity.KEY_KAKAO_NICK_NAME) ?: ""
        )
    }

    fun onClickSignUp() {
        viewModelScope.launch(mainDispatcher) {
            _event.emit(SignUpViewEvent.SignUp)
        }
    }

    fun signUp(
        kakaoId: Long,
        image: ImageBitmap,
        nickname: String,
        bio: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(SignUpState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            signUpUseCase(
                kakaoId = kakaoId,
                image = convertBitmapToWebpFile(image),
                nickname = nickname,
                bio = bio
            ).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(SignUpState.SignUpSuccess(state.data))
                    is ResponseState.Error -> _state.emit(SignUpState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }
}