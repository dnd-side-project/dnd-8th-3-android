package com.d83t.bpm.presentation.ui.making_reservation

import com.d83t.bpm.domain.model.Schedule

sealed interface MakingReservationState {
    object Init : MakingReservationState
    object Loading: MakingReservationState
    data class SaveSuccess(val schedule: Schedule) : MakingReservationState
    object Error: MakingReservationState
}