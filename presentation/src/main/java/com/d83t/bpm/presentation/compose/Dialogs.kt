package com.d83t.bpm.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
inline fun BaseDialog(
    state: MutableState<Boolean>,
    isCancelable: Boolean = true,
    noinline onDismissRequest: (() -> Unit)? = null,
    crossinline block: @Composable () -> Unit,
) {
    if (state.value) {
        Dialog(
            onDismissRequest = {
                state.value = false
                onDismissRequest?.invoke()
            },
            properties = DialogProperties(
                dismissOnBackPress = isCancelable,
                dismissOnClickOutside = isCancelable
            )
        ) {
            block()
        }
    }
}