package com.d83t.bpm.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.d83t.bpm.presentation.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("bind:list_item")
fun RecyclerView.bindListItems(list: List<Any>?) {
    if (adapter != null) {
        (adapter as ListAdapter<Any, *>).submitList(list ?: emptyList())
    }
}

@BindingAdapter("bind:review_chips")
fun ChipGroup.bindChips(reviewList: List<String>?) {
    if (reviewList.isNullOrEmpty()) return else {
        reviewList.forEach {
            addView(
                Chip(context).apply {
                    text = it
                    setChipBackgroundColorResource(R.color.gray_12)
                }
            )
        }
    }
}

@BindingAdapter("bind:image_url")
fun ImageView.bindImageUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .thumbnail(0.1f)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
