package com.d83t.bpm.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.NetworkError
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.splash.GetKakaoUserIdUseCase
import com.d83t.bpm.domain.usecase.splash.GetUserTokenUseCase
import com.d83t.bpm.domain.usecase.splash.SendKakaoUserIdVerificationUseCase
import com.d83t.bpm.domain.usecase.splash.SetKakaoUserIdUseCase
import com.d83t.bpm.domain.usecase.splash.SetUserTokenUseCase
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
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getKakaoUserIdUseCase: GetKakaoUserIdUseCase,
    private val setKakaoUserIdUseCase: SetKakaoUserIdUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val sendKakaoUserIdVerificationUseCase: SendKakaoUserIdVerificationUseCase,
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
                SplashState.NoUserInfo -> Unit
                else -> Unit
            }
        }
    }

    // 1. ????????? ????????? ???????????? ?????? ?????? ????????????
    // state - Init
    fun getStoredUserInfo() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getKakaoUserIdUseCase().zip(getUserTokenUseCase()) { kakaoId, userToken ->
                Pair(kakaoId, userToken)
            }.onEach {
                // ?????? ??? ?????? ?????? ?????? ?????? ???????????? ??????
                // TODO : ????????? ?????? validation ?????? ??? ??? ????????? ??????
                if (it.first == null || it.second == null) {
                    _state.emit(SplashState.NoUserInfo)
                } else {
                    // ?????? ??? ?????? ?????? ?????? ?????????????????? stop
                    _state.emit(SplashState.Finish)
                }
            }.launchIn(viewModelScope)
        }
    }

    // 2. ????????? SDK?????? ????????? ??????ID set
    // state - Init, SignIn
    fun setKakaoUserId(kakaoUserId: Long, kakaoNickName : String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            setKakaoUserIdUseCase(kakaoUserId).onEach { kakaoUserId ->
                withContext(mainDispatcher) {
                    kakaoUserId?.let { _state.emit(SplashState.ValidationCheck(it, kakaoNickName)) }
                }
            }.launchIn(viewModelScope)
        }
    }

    // 3. ???????????? ????????? UserId??? Valid Check ?????? ??????
    // ?????? ?????? ???????????? ?????? ?????? ?????? ?????? ?????? ???????????? ??????
    // state - ValidationCheck
    fun sendKakaoIdVerification(kakaoUserId: Long, kakaoNickName : String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            sendKakaoUserIdVerificationUseCase(kakaoUserId).onEach { state ->
                withContext(mainDispatcher) {
                    when (state) {
                        is ResponseState.Success -> _state.emit(SplashState.SaveToken(state.data.token))
                        is ResponseState.Error -> {
                            if (state.error.code == CODE_NOT_FOUND_USER_ID) {
                                // TODO : ????????? SDK?????? ???????????? ?????? ?????????
                                // ?????? ?????? ????????????..
                                _state.emit(SplashState.SignUp(kakaoUserId, kakaoNickName))
                            } else {
                                _state.emit(SplashState.Error(state.error))
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    // 4. ???????????? ?????? ?????? ?????? ??????
    // ?????? ?????? ???????????? ??????
    // state - SaveToken
    fun saveUserToken(token: String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            setUserTokenUseCase(token).onEach {
                withContext(mainDispatcher) {
                    if (!it.isNullOrEmpty()) {
                        _state.emit(SplashState.Finish)
                    } else {
                        _state.emit(SplashState.Error(NetworkError()))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    companion object {
        private const val CODE_NOT_FOUND_USER_ID = "404"

    }
}