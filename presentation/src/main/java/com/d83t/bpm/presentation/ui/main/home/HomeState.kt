package com.d83t.bpm.presentation.ui.main.home

sealed interface HomeState {
    object Init : HomeState
    object StudioList : HomeState

    object Error : HomeState
}