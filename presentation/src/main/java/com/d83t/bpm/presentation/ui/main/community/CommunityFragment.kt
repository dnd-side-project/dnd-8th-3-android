package com.d83t.bpm.presentation.ui.main.community

import androidx.fragment.app.viewModels
import com.d83t.bpm.presentation.base.BaseFragment
import com.d83t.bpm.presentation.databinding.FragmentCommunityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment :
    BaseFragment<FragmentCommunityBinding>(FragmentCommunityBinding::inflate) {

    override val viewModel: CommunityViewModel by viewModels()

    override fun initLayout() = Unit

    companion object {

        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }
}