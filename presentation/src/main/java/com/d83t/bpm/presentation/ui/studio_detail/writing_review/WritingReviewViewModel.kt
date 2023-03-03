package com.d83t.bpm.presentation.ui.studio_detail.writing_review

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.splash.WriteReviewUseCase
import com.d83t.bpm.domain.usecase.studio_detail.StudioDetailUseCase
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.IoDispatcher
import com.d83t.bpm.presentation.di.MainDispatcher
import com.d83t.bpm.presentation.util.convertBitmapToWebpFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritingReviewViewModel @Inject constructor(
    private val writeReviewUseCase: WriteReviewUseCase,
    private val studioDetailUseCase: StudioDetailUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<WritingReviewViewEvent>()
    val event: SharedFlow<WritingReviewViewEvent>
        get() = _event

    private val _state = MutableStateFlow<WritingReviewState>(WritingReviewState.Init)
    val state: StateFlow<WritingReviewState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun getStudio(
        studioId: Int
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(WritingReviewState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            studioDetailUseCase(studioId = studioId).onEach { state ->
                when(state) {
                    is ResponseState.Success -> _state.emit(WritingReviewState.StudioSuccess(state.data))
                    is ResponseState.Error -> _state.emit(WritingReviewState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun writeReview(
        studioId: Int,
        images: List<ImageBitmap>,
        rating: Double,
        recommends: List<String>,
        content: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(WritingReviewState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            writeReviewUseCase(
                studioId = studioId,
                images = images.map { convertBitmapToWebpFile(it) },
                rating = rating,
                recommends = recommends,
                content = content
            ).onEach { state ->
                when(state) {
                    is ResponseState.Success -> _state.emit(WritingReviewState.ReviewSuccess(state.data))
                    is ResponseState.Error -> _state.emit(WritingReviewState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onClickWriteReview() {
        viewModelScope.launch(mainDispatcher) {
            _event.emit(WritingReviewViewEvent.Write)
        }
    }
}