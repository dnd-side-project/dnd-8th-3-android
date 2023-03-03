package com.d83t.bpm.presentation.ui.main.home.recommend.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.presentation.databinding.ItemHomeRecommendBinding

class HomeRecommendViewHolder(
    private val binding: ItemHomeRecommendBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Studio, listener: (Int?) -> Unit) {
        binding.item = item
        binding.root.setOnClickListener {
            listener.invoke(item.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HomeRecommendViewHolder {
            val binding = ItemHomeRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return HomeRecommendViewHolder(binding)
        }
    }
}
