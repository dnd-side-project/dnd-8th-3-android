package com.d83t.bpm.presentation.ui.main.add

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseBottomSheetFragment
import com.d83t.bpm.presentation.databinding.BottomsheetMainAddBinding
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAddBottomSheet :
    BaseBottomSheetFragment<BottomsheetMainAddBinding>(BottomsheetMainAddBinding::inflate) {

    override fun getTheme(): Int = R.style.DdoreumBottomSheetDialog

    override val viewModel: MainAddViewModel by viewModels()

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    MainAddViewEvent.Click -> {
                        requireContext().showToast("오픈 예정입니다!")
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(): MainAddBottomSheet {
            return MainAddBottomSheet()
        }
    }
}