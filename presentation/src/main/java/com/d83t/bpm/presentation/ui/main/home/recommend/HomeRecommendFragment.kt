package com.d83t.bpm.presentation.ui.main.home.recommend

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentHomeRecommendBinding
import com.d83t.bpm.presentation.ui.main.home.recommend.list.HomeRecommendAdapter
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeRecommendFragment :
    BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::inflate) {

    override val viewModel: HomeRecommendViewModel by viewModels()

    private val recommendAdapter: HomeRecommendAdapter by lazy {
        HomeRecommendAdapter { goToStudioDetail(it) }
    }

    override fun initLayout() {
        bind {
            list.adapter = recommendAdapter

        }
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    HomeRecommendState.Init -> {
                        viewModel.getStudioList()
                    }
                    HomeRecommendState.List -> Unit
                    HomeRecommendState.Error -> {
                        // TODO : Error Handling
                        requireContext().showToast("리스트 로드 중 에러가 발생했습니다.")
                    }
                }
            }
        }
    }

    private fun goToStudioDetail(studioId: Int?) {
        studioId?.let {

        }
    }

    companion object {

        const val KEY_TYPE = "KEY_TYPE"

        fun newInstance(type: String): HomeRecommendFragment {
            return HomeRecommendFragment().apply {
                arguments = bundleOf(
                    KEY_TYPE to type
                )
            }
        }
    }
}