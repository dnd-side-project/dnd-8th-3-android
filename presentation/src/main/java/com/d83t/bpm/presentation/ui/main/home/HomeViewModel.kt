package com.d83t.bpm.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.domain.usecase.main.GetStudioListUseCase
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStudioListUseCase: GetStudioListUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state: StateFlow<HomeState>
        get() = _state

    private val _event = MutableSharedFlow<HomeViewEvent>()
    val event: SharedFlow<HomeViewEvent>
        get() = _event

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    private val _studioList = MutableStateFlow<List<Studio>>(emptyList())
    val studioList: StateFlow<List<Studio>>
        get() = _studioList

    fun getStudioList() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getStudioListUseCase(
                limit = 10,
                offset = 0
            ).onEach { state ->
                when (state) {
                    is ResponseState.Success -> {
                        _studioList.emit(state.data.studios ?: emptyList())
                        _state.emit(HomeState.StudioList)
                    }
                    is ResponseState.Error -> {
                        _state.emit(HomeState.Error)
                    }
                }
            }.launchIn(viewModelScope)
        }

    }


}