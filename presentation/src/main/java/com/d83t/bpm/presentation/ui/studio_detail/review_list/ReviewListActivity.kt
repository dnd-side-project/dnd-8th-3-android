package com.d83t.bpm.presentation.ui.studio_detail.review_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.ReviewComposable
import com.d83t.bpm.presentation.compose.ReviewListHeader
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListActivity : BaseComponentActivity() {
    override val viewModel: ReviewListViewModel by viewModels()

    private var studioId: Int = 0
    private val reviewListState = mutableStateOf<List<Review>>(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studioId = intent.getIntExtra("studioId", 1)

        initComposeUi {
            ReviewListActivityContent(reviewListState = reviewListState)
        }
    }

    override fun initUi() = Unit

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ReviewListState.Init -> viewModel.getReviewList(studioId = studioId)
                    is ReviewListState.Success -> {
                        hideLoadingScreen()
                        reviewListState.value = state.reviewList
                    }
                    is ReviewListState.Error -> hideLoadingScreen()
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReviewListActivity::class.java)
        }

    }
}

@Composable
private fun ReviewListActivityContent(
    reviewListState: MutableState<List<Review>>
) {
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
                reviewCount = reviewListState.value.size,
                showImageReviewOnlyState = showImageReviewOnlyState,
                showReviewOrderByLikeState = showReviewOrderByLikeState,
            )
        }

        items(reviewListState.value) { review ->
            ReviewComposable(
                modifier = Modifier.padding(horizontal = 16.dp),
                review = review
            )
        }
    }
}