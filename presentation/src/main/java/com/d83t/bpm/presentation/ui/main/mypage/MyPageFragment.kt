package com.d83t.bpm.presentation.ui.main.mypage

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentMypageBinding
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    override val viewModel: MyPageViewModel by viewModels()

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
                    MyPageViewEvent.Click -> {
                        requireContext().showToast("오픈 예정입니다!")
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}