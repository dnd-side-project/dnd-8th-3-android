package com.d83t.bpm.presentation.ui.studio_detail.review_list

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.ReviewComposable
import com.d83t.bpm.presentation.compose.ReviewListHeader
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.BPMTheme

class ReviewListActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun initUi() {
        setContent {
            BPMTheme {
                ReviewListActivityContent()
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private fun ReviewListActivityContent() {
    val showImageReviewOnlyState = remember { mutableStateOf(false) }
    val showReviewOrderByLikeState = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            ScreenHeader(header = "리뷰 전체보기")
        }

        item {
            ReviewListHeader(
                reviewCount = 0, // TODO : Will Be Changed
                showImageReviewOnlyState = showImageReviewOnlyState,
                showReviewOrderByLikeState = showReviewOrderByLikeState,
                onClickWriteReview = { }
            )
        }

    }
}