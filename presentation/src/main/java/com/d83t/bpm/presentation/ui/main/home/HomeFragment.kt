package com.d83t.bpm.presentation.ui.main.home

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() = Unit

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}