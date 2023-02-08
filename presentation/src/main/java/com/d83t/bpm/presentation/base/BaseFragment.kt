package com.d83t.bpm.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    Fragment() {

    lateinit var binding: T

    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        setupCollect()
    }

    protected abstract fun initLayout()

    protected open fun setupCollect() = Unit

    protected fun bind(action: T.() -> Unit) {
        binding.run(action)
    }

}