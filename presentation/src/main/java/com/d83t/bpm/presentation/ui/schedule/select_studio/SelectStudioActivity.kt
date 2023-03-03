package com.d83t.bpm.presentation.ui.schedule.select_studio

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectStudioActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComposeUi {

        }
    }

    override fun initUi() = Unit

    override fun setupCollect() {

    }
}

@Composable
private fun SelectStudioActivityContent(

) {

}