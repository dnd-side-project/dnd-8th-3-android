package com.d83t.bpm.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.usecase.splash.GetKakaoUserIdUseCase
import com.d83t.bpm.domain.usecase.splash.SetKakaoUserIdUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
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
import kotlinx.coroutines.withContext

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getKakaoUserIdUseCase: GetKakaoUserIdUseCase,
    private val setKakaoUserIdUseCase: SetKakaoUserIdUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _event = MutableSharedFlow<SplashViewEvent>()
    val event: SharedFlow<SplashViewEvent>
        get() = _event

    private val _state = MutableStateFlow<SplashState>(SplashState.Init)
    val state: StateFlow<SplashState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            when (state.value) {
                SplashState.SignIn -> {
                    // TODO : Exception Handling
                }
                else -> {
                    // TODO : Exception Handling
                }
            }

        }
    }

    fun getStoredId() {
        viewModelScope.launch(ioDispatcher) {
            getKakaoUserIdUseCase().onEach {
                withContext(mainDispatcher) {
                    _state.emit(SplashState.SignUp(it))
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setLoginId(kakaoId: String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            setKakaoUserIdUseCase(kakaoId).onEach {
                withContext(mainDispatcher) {
                    _state.emit(SplashState.SignUp(it))
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setFinish() {
        viewModelScope.launch {
            _state.emit(SplashState.Finish)
        }
    }
}