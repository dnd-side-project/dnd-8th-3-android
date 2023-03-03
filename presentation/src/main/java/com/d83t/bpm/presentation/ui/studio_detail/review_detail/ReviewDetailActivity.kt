package com.d83t.bpm.presentation.ui.studio_detail.review_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.LikeButton
import com.d83t.bpm.presentation.compose.ReviewKeywordChip
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.dateOnly
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDetailActivity : BaseComponentActivity() {
    override val viewModel: ReviewDetailViewModel by viewModels()

    private var studioId: Int = 0
    private var reviewId: Int = 0
    private val reviewState = mutableStateOf<Review?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studioId = intent.getIntExtra("studioId", 0)
        reviewId = intent.getIntExtra("reviewId", 0)

        initComposeUi {
            reviewState.value?.let {
                ReviewDetailActivityContent(
                    review = it,
                    onClickLike = {}
                )
            } ?: run {

            }
        }
    }

    override fun initUi() = Unit

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ReviewDetailState.Init -> {
                        showLoadingScreen()
                        viewModel.getReviewDetail(
                            studioId = studioId,
                            reviewId = reviewId
                        )
                    }
                    is ReviewDetailState.Success -> {
                        hideLoadingScreen()
                        reviewState.value = state.review
                    }
                    is ReviewDetailState.Error -> Unit
                }
            }
        }

    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ReviewDetailActivity::class.java)
        }

    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
private inline fun ReviewDetailActivityContent(
    review: Review,
    crossinline onClickLike: () -> Unit
) {
    val scrollState = rememberScrollState()
    val likeState = remember { mutableStateOf(false) }
    val horizontalPagerState = rememberPagerState()

    with(review) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .background(color = GrayColor11)
        ) {
            Column(modifier = Modifier.background(color = Color.White)) {
                ScreenHeader(header = "리뷰 전체보기")

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        horizontalArrangement = SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.align(CenterVertically),
                            verticalAlignment = CenterVertically
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .clip(shape = CircleShape)
                                    .size(24.dp),
                                model = author?.profilePath ?: "",
                                contentDescription = "profileImage"
                            )

                            BPMSpacer(width = 8.dp)

                            Text(
                                text = author?.nickname ?: "",
                                fontWeight = SemiBold,
                                fontSize = 14.sp,
                                letterSpacing = 0.sp
                            )
                        }

                        Row(modifier = Modifier.align(CenterVertically)) {
                            Text(
                                text = createdAt?.dateOnly() ?: "",
                                fontWeight = SemiBold,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp,
                                color = GrayColor3
                            )

                            BPMSpacer(width = 8.dp)

                            Text(
                                text = "신고",
                                fontWeight = Medium,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp,
                                color = GrayColor3
                            )
                        }
                    }
                }

                Divider(color = GrayColor9)

                if (recommends?.size!! > 0) {
                    BPMSpacer(height = 10.dp)

                    FlowRow(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        mainAxisSpacing = 4.dp,
                        crossAxisSpacing = 6.dp
                    ) {
                        recommends?.forEach { keyword ->
                            ReviewKeywordChip(text = keyword)
                        }
                    }

                    BPMSpacer(height = 10.dp)

                    Divider(color = GrayColor9)
                }

                if (filesPath?.size!! > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.85f)
                    ) {
                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.85f),
                            state = horizontalPagerState,
                            count = filesPath?.size ?: 0
                        ) { index ->
                            GlideImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.85f),
                                model = filesPath?.get(index),
                                contentDescription = "studioImage",
                                contentScale = Crop
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    bottom = 16.dp
                                )
                                .clip(RoundedCornerShape(40.dp))
                                .width(42.dp)
                                .height(25.dp)
                                .background(color = FilteredWhiteColor)
                                .align(Alignment.BottomStart)
                        ) {
                            Text(
                                modifier = Modifier.align(Center),
                                text = "${review.filesPath?.size}/${horizontalPagerState.currentPage + 1}",
                                fontWeight = Normal,
                                fontSize = 12.sp,
                                letterSpacing = 2.sp
                            )
                        }
                    }
                }

                BPMSpacer(height = 20.dp)

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row {
                        if (rating != null) {
                            for (i in 1..5) {
                                Image(
                                    modifier = Modifier.size(15.dp),
                                    painter = painterResource(
                                        id = if (i.toDouble() <= rating!!) R.drawable.ic_star_small_filled
                                        else if (i.toDouble() > rating!! && rating!! > i - 1) R.drawable.ic_star_small_half
                                        else R.drawable.ic_star_small_empty
                                    ),
                                    contentDescription = "starIcon"
                                )
                            }
                        } else {
                            repeat(5) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star_small_empty),
                                    contentDescription = "starIcon",
                                    tint = GrayColor6
                                )

                                BPMSpacer(width = 2.dp)
                            }
                        }
                    }

                    BPMSpacer(height = 10.dp)

                    Text(
                        text = content ?: "",
                        fontWeight = Normal,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 19.sp
                    )

                    BPMSpacer(height = 20.dp)

                    LikeButton(
                        liked = review.liked ?: false,
                        likeState = likeState,
                        likeCount = likeCount ?: 0,
                        onClick = { onClickLike() }
                    )
                }

                BPMSpacer(height = 25.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
}