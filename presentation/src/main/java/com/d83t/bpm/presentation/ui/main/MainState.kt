package com.d83t.bpm.presentation.ui.main

sealed interface MainState {
    object Init : MainState
    data class SampleText(val text : String) : MainState
}