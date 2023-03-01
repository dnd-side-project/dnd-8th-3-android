package com.d83t.bpm.presentation.ui.making_reservation

sealed interface MakingReservationViewEvent {
    object Save : MakingReservationViewEvent
}