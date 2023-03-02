package com.d83t.bpm.presentation.ui.schedule

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.usecase.schedule.GetScheduleUseCase
import com.d83t.bpm.domain.usecase.schedule.SaveScheduleUseCase
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
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val saveScheduleUseCase: SaveScheduleUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _event = MutableSharedFlow<ScheduleViewEvent>()
    val event: SharedFlow<ScheduleViewEvent>
        get() = _event

    private val _state = MutableStateFlow<ScheduleState>(ScheduleState.Init)
    val state: StateFlow<ScheduleState>
        get() = _state

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    }

    init {
        getSchedule()
    }

    fun onClickSave() {
        viewModelScope.launch(mainDispatcher) {
            _event.emit(ScheduleViewEvent.Save)
        }
    }

    private fun getSchedule() {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(ScheduleState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getScheduleUseCase().onEach { state ->
                when(state) {
                    is ResponseState.Success -> _state.emit(ScheduleState.GetSuccess(state.data))
                    is ResponseState.Error -> _state.emit(ScheduleState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun saveSchedule(
        studioName: String,
        date: String,
        time: String,
        memo: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            _state.emit(ScheduleState.Loading)
        }

        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            saveScheduleUseCase(
                studioName = studioName,
                date = date,
                time = modifyTimeToFormat(time),
                memo = memo
            ).onEach { state ->
                when (state) {
                    is ResponseState.Success -> _state.emit(ScheduleState.SaveSuccess(state.data))
                    is ResponseState.Error -> _state.emit(ScheduleState.Error)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun modifyTimeToFormat(
        time: String
    ): String {
        val stringBuilder = StringBuilder()
        val timeInList = time.dropLast(5).split(":")

        stringBuilder
            .append(if (time.contains("오후")) timeInList[0].toInt() + 12 else timeInList[0])
            .append(":")
            .append(timeInList[1])
            .append(":00")

        return stringBuilder.toString()
    }
}