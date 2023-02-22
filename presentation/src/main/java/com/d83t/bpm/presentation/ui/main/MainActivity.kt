package com.d83t.bpm.presentation.ui.main

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.commitNow
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseActivity
import com.d83t.bpm.presentation.databinding.ActivityMainBinding
import com.d83t.bpm.presentation.ui.main.community.CommunityFragment
import com.d83t.bpm.presentation.ui.main.home.HomeFragment
import com.d83t.bpm.presentation.ui.main.mypage.MyPageFragment
import com.d83t.bpm.presentation.ui.main.notification.NotificationFragment
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }

        setUpNavigation()
    }

    override fun setupCollect() {
        // TODO : collect any SharedFlows, StateFlows

        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    MainState.Init -> {
                        // TODO : get info for any data
                    }
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    MainViewEvent.Add -> {
                        showToast("Add Clicked")
                    }
                }
            }
        }
    }


    private fun setUpNavigation() {
        bind {
            val currentFragment = supportFragmentManager.primaryNavigationFragment
            if (currentFragment == null) {
                changeFragment(binding.mainTab.selectedItemId)
            }

            mainTab.setOnItemSelectedListener {
                changeFragment(it.itemId)
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun changeFragment(fragmentId: Int) {
        val fragment = when (fragmentId) {
            R.id.nav_noti -> {
                NotificationFragment.newInstance()
            }
            R.id.nav_home -> {
                HomeFragment.newInstance()
            }
            R.id.nav_community -> {
                CommunityFragment.newInstance()
            }
            R.id.nav_mypage -> {
                MyPageFragment.newInstance()
            }
            else -> {
                HomeFragment.newInstance()
            }
        }

        supportFragmentManager.commitNow(true) {
            val currentFragment = supportFragmentManager.primaryNavigationFragment
            if (currentFragment != null) {
                hide(currentFragment)
            }

            var newFragment =
                supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
            if (newFragment == null) {
                newFragment = fragment
                add(R.id.container, newFragment, fragment::class.java.simpleName)
            } else {
                show(newFragment)
            }

            setPrimaryNavigationFragment(newFragment)
            setReorderingAllowed(true)
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }
}