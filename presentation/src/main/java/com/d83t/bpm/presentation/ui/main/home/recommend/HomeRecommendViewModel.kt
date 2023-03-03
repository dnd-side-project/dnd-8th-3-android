package com.d83t.bpm.presentation.ui.main.home.recommend

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeRecommendViewModel @Inject constructor(
    private val getStudioListUseCase: GetStudioListUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow<HomeRecommendState>(HomeRecommendState.Init)
    val state: StateFlow<HomeRecommendState>
        get() = _state

    private val _event = MutableSharedFlow<HomeRecommendViewEvent>()
    val event: SharedFlow<HomeRecommendViewEvent>
        get() = _event

    private val _list = MutableStateFlow<List<Studio>>(emptyList())
    val list: StateFlow<List<Studio>>
        get() = _list

    val type: String by lazy {
        savedStateHandle.get<String>(HomeRecommendFragment.KEY_TYPE) ?: ""
    }

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun getStudioList() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getStudioListUseCase(limit = 10, offset = 0).onEach { state ->
                when (state) {
                    is ResponseState.Success -> {
                        _list.emit(state.data.studios ?: emptyList())
                        _state.emit(HomeRecommendState.List)
                    }
                    is ResponseState.Error -> {
                        _state.emit(HomeRecommendState.Error)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun clickStudioDetail(studioId: Int?) {
        viewModelScope.launch {
            _event.emit(HomeRecommendViewEvent.ClickDetail(studioId))
        }
    }
}