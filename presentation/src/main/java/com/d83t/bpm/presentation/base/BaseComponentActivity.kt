package com.d83t.bpm.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseComponentActivity : ComponentActivity() {

    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        setupCollect()
    }

    protected abstract fun initUi()

    protected abstract fun setupCollect()

}