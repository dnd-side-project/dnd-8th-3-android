package com.d83t.bpm.presentation.ui.main.home

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentHomeBinding
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    HomeState.Init -> {
                        viewModel.getStudioList()
                    }
                    HomeState.StudioList -> Unit
                    HomeState.Error -> {
                        // TODO : Error Handling
                        requireContext().showToast("리스트 로드 중 에러가 발생했습니댜.")
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}