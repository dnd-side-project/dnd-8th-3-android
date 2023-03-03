package com.d83t.bpm.presentation.ui.schedule.select_studio

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.search_studio.SearchStudioUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectStudioViewModel @Inject constructor(
    private val searchStudioUseCase: SearchStudioUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<SelectStudioViewEvent>()
    val event: SharedFlow<SelectStudioViewEvent>
        get() = _event

    private val _state = MutableStateFlow<SelectStudioState>(SelectStudioState.Init)
    val state: StateFlow<SelectStudioState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun onClickSearch() {
        viewModelScope.launch(mainDispatcher) {
            _event.emit(SelectStudioViewEvent.Search)
        }
    }

    fun searchStudio(
        query: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(SelectStudioState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            searchStudioUseCase(
                query = query
            ).onEach { state ->
                when(state) {
                    is ResponseState.Success -> _state.emit(SelectStudioState.Success(state.data.studios ?: emptyList()))
                    is ResponseState.Error -> _state.emit(SelectStudioState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }
}