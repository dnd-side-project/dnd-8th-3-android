package com.d83t.bpm.presentation.ui.main.home.recommend.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.d83t.bpm.domain.model.Studio

class HomeRecommendAdapter(
    private val listener: (Int?) -> Unit
) : ListAdapter<Studio, HomeRecommendViewHolder>(HomeRecommendListDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeRecommendViewHolder {
        return HomeRecommendViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    companion object {
        object HomeRecommendListDiffUtil : DiffUtil.ItemCallback<Studio>() {
            override fun areItemsTheSame(
                oldItem: Studio,
                newItem: Studio
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Studio,
                newItem: Studio
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}