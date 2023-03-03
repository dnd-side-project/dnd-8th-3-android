package com.d83t.bpm.presentation.ui.main.home.recommend.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.d83t.bpm.domain.model.Studio

class HomeRecommendImageAdapter(
) : ListAdapter<String, HomeRecommendImageViewHolder>(HomeRecommendImageListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendImageViewHolder {
        return HomeRecommendImageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeRecommendImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class HomeRecommendImageListDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}