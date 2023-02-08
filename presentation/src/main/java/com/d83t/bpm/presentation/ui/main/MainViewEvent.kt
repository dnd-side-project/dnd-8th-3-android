package com.d83t.bpm.presentation.ui.main

sealed interface MainViewEvent {
    object Click : MainViewEvent
}