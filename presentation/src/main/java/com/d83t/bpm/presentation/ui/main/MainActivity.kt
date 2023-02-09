package com.d83t.bpm.presentation.ui.main

import androidx.activity.viewModels
import com.d83t.bpm.presentation.base.BaseActivity
import com.d83t.bpm.presentation.databinding.ActivityMainBinding
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun initLayout() {
        // TODO : setup view, ui

    }

    override fun setupCollect() {
        // TODO : collect any SharedFlows, StateFlows

        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    MainState.Init -> {
                        viewModel.getInitText()
                    }
                    is MainState.SampleText -> {
                        showToast(state.text)
                    }
                }
            }

        }
    }
}