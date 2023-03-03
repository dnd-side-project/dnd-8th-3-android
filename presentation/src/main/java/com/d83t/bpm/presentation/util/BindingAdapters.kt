package com.d83t.bpm.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.d83t.bpm.presentation.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat

@BindingAdapter("bind:visibility")
fun View.bindVisibleOrGone(isVisibleOrGone: Boolean?) {
    visibility = if (isVisibleOrGone == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("bind:visibility")
fun View.bindVisibleOrGone(text: String?) {
    visibility = if (!text.isNullOrEmpty()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


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

@BindingAdapter("bind:home_user_schedule_date", "bind:home_user_schedule_time")
fun AppCompatTextView.bindHomeUserSchedule(dateString: String?, timeString: String?) {
    if (dateString.isNullOrEmpty() || timeString.isNullOrEmpty() || dateString == "-" || timeString == "-") return
    else {
        val date = DateTime.parse(dateString, DateTimeFormat.forPattern("yyyy-MM-dd"))
        val time = DateTime.parse(timeString, DateTimeFormat.forPattern("HH:mm:ss"))

        text = "${date.toString("yyyy년 MM월 dd일 ")} ${time.getKoreanHour()}"
    }
}

@BindingAdapter("bind:home_user_schedule_dday")
fun AppCompatTextView.bindHomeUserScheduleDday(dateString: String?) {
    if (dateString.isNullOrEmpty() || dateString == "-") return
    else {
        val date = DateTime.parse(dateString, DateTimeFormat.forPattern("yyyy-MM-dd"))
        text = "D${Days.daysBetween(date, DateTime.now()).days}"
    }
}
