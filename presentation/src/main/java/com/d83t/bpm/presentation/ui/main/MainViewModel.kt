package com.d83t.bpm.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _event = MutableSharedFlow<MainViewEvent>()
    val event: SharedFlow<MainViewEvent>
        get() = _event

    private val _state = MutableStateFlow<MainState>(MainState.Init)
    val state: StateFlow<MainState>
        get() = _state

    fun onClickAdd(){
        viewModelScope.launch(mainDispatcher) {
            _event.emit(MainViewEvent.Add)
        }
    }

}