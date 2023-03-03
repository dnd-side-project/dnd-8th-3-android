package com.d83t.bpm.presentation.ui.main.home.recommend.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d83t.bpm.presentation.databinding.ItemHomeRecommendImageBinding

class HomeRecommendImageViewHolder(
    private val binding: ItemHomeRecommendImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.item = item
    }

    companion object {
        fun create(parent: ViewGroup): HomeRecommendImageViewHolder {
            val binding = ItemHomeRecommendImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeRecommendImageViewHolder(binding)
        }
    }
}
