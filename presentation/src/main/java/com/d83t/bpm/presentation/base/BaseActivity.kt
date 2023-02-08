package com.d83t.bpm.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val inflater: (LayoutInflater) -> T) :
    AppCompatActivity() {

    lateinit var binding: T

    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)

        initLayout()
        setupCollect()
    }

    protected abstract fun initLayout()

    protected abstract fun setupCollect()

    protected fun bind(action: T.() -> Unit) {
        binding.run(action)
    }
}