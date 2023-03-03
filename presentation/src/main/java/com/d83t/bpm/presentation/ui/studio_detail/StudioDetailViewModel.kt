package com.d83t.bpm.presentation.ui.studio_detail

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.review.GetReviewListUseCase
import com.d83t.bpm.domain.usecase.studio_detail.StudioDetailUseCase
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
class StudioDetailViewModel @Inject constructor(
    private val studioDetailUseCase: StudioDetailUseCase,
    private val reviewListUseCase: GetReviewListUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<StudioDetailViewEvent>()
    val event: SharedFlow<StudioDetailViewEvent>
        get() = _event

    private val _state = MutableStateFlow<StudioDetailState>(StudioDetailState.Init)
    val state: StateFlow<StudioDetailState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun getStudioDetail(
        studioId: Int
    ) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            studioDetailUseCase(studioId = studioId).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(StudioDetailState.StudioDetailSuccess(state.data))
                    is ResponseState.Error -> _state.emit(StudioDetailState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getReviewList(
        studioId: Int
    ) {
        viewModelScope.launch(mainDispatcher) {

        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            reviewListUseCase(studioId = studioId).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(StudioDetailState.ReviewListSuccess(state.data))
                    is ResponseState.Error -> _state.emit(StudioDetailState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }
}