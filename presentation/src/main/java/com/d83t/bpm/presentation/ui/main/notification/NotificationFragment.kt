package com.d83t.bpm.presentation.ui.main.notification

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    override val viewModel: NotificationViewModel by viewModels()

    override fun initLayout() = Unit

    companion object {

        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }
}