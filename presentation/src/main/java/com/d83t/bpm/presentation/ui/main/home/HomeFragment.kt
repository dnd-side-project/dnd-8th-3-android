package com.d83t.bpm.presentation.ui.main.home

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentHomeBinding
import com.d83t.bpm.presentation.ui.main.home.recommend.HomeRecommendFragment
import com.d83t.bpm.presentation.ui.schedule.ScheduleActivity
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var scheduleResultLauncher: ActivityResultLauncher<Intent>

    override val viewModel: HomeViewModel by viewModels()

    private val fragmentList: List<Fragment> by lazy {
        listOf(
            HomeRecommendFragment.newInstance(TYPE_HOT),
            HomeRecommendFragment.newInstance(TYPE_REVIEW),
            HomeRecommendFragment.newInstance(TYPE_OPEN)
        )
    }

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        scheduleResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.refreshUserSchedule()
            }

        setUpPager()
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    HomeState.Init -> {
                        viewModel.getUserSchedule()
                    }
                    HomeState.UserSchedule -> Unit
                    HomeState.Error -> {
                        // TODO : Error Handling
//                        requireContext().showToast("?????? ????????? ???????????? ??? ????????? ??????????????????.")
                    }
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    HomeViewEvent.ClickSearch -> {
                        requireContext().showToast("??????????????? ??????")
                    }
                    HomeViewEvent.ClickSchedule -> {
                        goToSchedule()
                    }
                }
            }
        }
    }

    private fun goToSchedule() {
        scheduleResultLauncher.launch(ScheduleActivity.newIntent(requireContext()))
    }

    private fun setUpPager() {
        binding.pager.adapter = HomePagerAdapter(requireActivity(), fragmentList)

        TabLayoutMediator(
            binding.tab,
            binding.pager,
            false,
            true
        ) { tab: TabLayout.Tab?, position: Int ->
            val resId: Int = when (position) {
                0 -> R.string.tab_hot
                1 -> R.string.tab_review
                2 -> R.string.tab_open
                else -> -1
            }

            if (resId != -1) {
                tab?.setText(resId)
            }
        }.attach()
    }

    companion object {

        val TYPE_HOT = "TYPE_HOT"
        val TYPE_REVIEW = "TYPE_REVIEW"
        val TYPE_OPEN = "TYPE_OPEN"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}