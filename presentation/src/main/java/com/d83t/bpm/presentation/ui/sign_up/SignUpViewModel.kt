package com.d83t.bpm.presentation.ui.sign_up

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.sign_up.SignUpUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import com.d83t.bpm.presentation.util.convertBitmapToWebpFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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