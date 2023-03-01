package com.d83t.bpm.presentation.ui.making_reservation

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.splash.making_reservation.SaveScheduleUseCase
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
class MakingReservationViewModel @Inject constructor(
    private val saveScheduleUseCase: SaveScheduleUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<MakingReservationViewEvent>()
    val event: SharedFlow<MakingReservationViewEvent>
        get() = _event

    private val _state = MutableStateFlow<MakingReservationState>(MakingReservationState.Init)
    val state: StateFlow<MakingReservationState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    fun onClickSave() {
        viewModelScope.launch(mainDispatcher) {
            _event.emit(MakingReservationViewEvent.Save)
        }
    }

    fun saveSchedule(
        studioName: String,
        date: String,
        time: String,
        memo: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(MakingReservationState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            saveScheduleUseCase(
                studioName = studioName,
                date = date,
                time = time,
                memo = memo
            ).onEach { state ->
                when(state) {
                    is ResponseState.Success -> _state.emit(MakingReservationState.SaveSuccess(state.data))
                    is ResponseState.Error -> _state.emit(MakingReservationState.Error)
                }
            }
        }
    }
}