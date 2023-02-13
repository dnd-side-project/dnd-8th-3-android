package com.d83t.bpm.presentation.base

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.d83t.bpm.presentation.compose.theme.BPMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseComponentActivity : ComponentActivity() {

    fun initUi(block: @Composable () -> Unit) {
        setContent {
            BPMTheme {
                block()
            }
        }
    }

}