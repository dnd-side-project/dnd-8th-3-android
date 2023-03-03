package com.d83t.bpm.presentation.ui.studio_detail.review_list

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.review.GetReviewListUseCase
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
class ReviewListViewModel @Inject constructor(
    private val reviewListUseCase: GetReviewListUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<ReviewListViewEvent>()
    val event: SharedFlow<ReviewListViewEvent>
        get() = _event

    private val _state = MutableStateFlow<ReviewListState>(ReviewListState.Init)
    val state: StateFlow<ReviewListState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun getReviewList(
        studioId: Int
    ) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            reviewListUseCase.invoke(studioId = studioId).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(ReviewListState.Success(state.data))
                    is ResponseState.Error -> _state.emit(ReviewListState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }
}