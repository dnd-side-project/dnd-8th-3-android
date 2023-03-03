package com.d83t.bpm.presentation.ui.studio_detail.writing_review

import com.d83t.bpm.domain.usecase.splash.WriteReviewUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WritingReviewViewModel @Inject constructor(
    private val writeReviewUseCase: WriteReviewUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<WritingReviewViewEvent>()
    val event: SharedFlow<WritingReviewViewEvent>
        get() = _event

    private val _state = MutableStateFlow<WritingReviewState>(WritingReviewState.Init)
    val state: StateFlow<WritingReviewState>
        get() = _state

    fun writeReview() {

    }

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }
}