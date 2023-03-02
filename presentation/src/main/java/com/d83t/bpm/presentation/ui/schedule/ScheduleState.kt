package com.d83t.bpm.presentation.ui.schedule

import com.d83t.bpm.domain.model.Schedule

sealed interface ScheduleState {
    object Init : ScheduleState
    object Loading: ScheduleState

    data class GetSuccess(val schedule: Schedule) : ScheduleState
    data class SaveSuccess(val schedule: Schedule) : ScheduleState
    object Error: ScheduleState
}