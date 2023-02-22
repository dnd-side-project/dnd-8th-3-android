package com.d83t.bpm.presentation.ui.main.mypage

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentHomeBinding
import com.d83t.bpm.presentation.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    override val viewModel: MyPageViewModel by viewModels()

    override fun initLayout() = Unit

    companion object {

        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}