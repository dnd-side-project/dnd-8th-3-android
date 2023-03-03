package com.d83t.bpm.presentation.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind:list_item")
fun RecyclerView.bindListItems(list: List<Any>?) {
    if (adapter != null) {
        (adapter as ListAdapter<Any, *>).submitList(list ?: emptyList())
    }
}