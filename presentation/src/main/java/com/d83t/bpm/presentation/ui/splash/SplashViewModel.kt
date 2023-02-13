package com.d83t.bpm.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.usecase.GetStoredIdUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getStoredIdUseCase: GetStoredIdUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _event = MutableSharedFlow<SplashViewEvent>()
    val event: SharedFlow<SplashViewEvent>
        get() = _event

    private val _state = MutableStateFlow<SplashState>(SplashState.Init)
    val state: StateFlow<SplashState>
        get() = _state

    fun getStoredId() {
        viewModelScope.launch(ioDispatcher) {
            val kakaoId = getStoredIdUseCase().first()
            withContext(mainDispatcher) {
                _state.emit(SplashState.KakaoId(kakaoId))
            }
        }
    }
}