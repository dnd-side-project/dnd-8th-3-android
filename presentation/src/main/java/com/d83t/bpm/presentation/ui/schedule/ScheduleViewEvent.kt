package com.d83t.bpm.presentation.ui.schedule

sealed interface ScheduleViewEvent {

    object Save : ScheduleViewEvent
}