package com.d83t.bpm.presentation.ui.studio_detail.review_detail

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.review.GetReviewDetailUseCase
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
class ReviewDetailViewModel @Inject constructor(
    private val getReviewDetailUseCase: GetReviewDetailUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<ReviewDetailViewEvent>()
    val event: SharedFlow<ReviewDetailViewEvent>
        get() = _event

    private val _state = MutableStateFlow<ReviewDetailState>(ReviewDetailState.Init)
    val state: StateFlow<ReviewDetailState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun getReviewDetail(
        studioId: Int,
        reviewId: Int
    ) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getReviewDetailUseCase(
                studioId = studioId,
                reviewId = reviewId
            ).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(ReviewDetailState.Success(state.data))
                    is ResponseState.Error -> _state.emit(ReviewDetailState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }
}