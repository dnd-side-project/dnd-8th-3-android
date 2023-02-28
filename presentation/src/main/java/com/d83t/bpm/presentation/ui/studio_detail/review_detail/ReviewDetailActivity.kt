package com.d83t.bpm.presentation.ui.studio_detail.review_detail

import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.LikeButton
import com.d83t.bpm.presentation.compose.ReviewKeywordChip
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

class ReviewDetailActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun initUi() {
        setContent {
            BPMTheme {
                ReviewDetailActivityContent(
                    onClickLike = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@OptIn(ExperimentalPagerApi::class, ExperimentalLayoutApi::class)
@Composable
private inline fun ReviewDetailActivityContent(
    crossinline onClickLike: () -> Unit
) {
    val scrollState = rememberScrollState()
    val likeState = remember { mutableStateOf(false) }

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
                        Image(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.default_profile_image),
                            contentDescription = "profileImage"
                        )

                        BPMSpacer(width = 8.dp)

                        Text(
                            text = "닉네임",
                            fontWeight = SemiBold,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )
                    }

                    Row(modifier = Modifier.align(CenterVertically)) {
                        Text(
                            text = "2022.11.10",
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

            BPMSpacer(height = 10.dp)

            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 6.dp
            ) {
                listOf("깔끔해요!", "친절해요!").forEach { keyword ->
                    ReviewKeywordChip(text = keyword)
                }
            }

            BPMSpacer(height = 10.dp)

            Divider(color = GrayColor9)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.85f),
                    count = 1
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.85f),
                        painter = painterResource(id = R.drawable.dummy_studio),
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
                        text = "1/1",
                        fontWeight = Normal,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                }
            }

            BPMSpacer(height = 20.dp)

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_small),
                            contentDescription = "starIcon",
                            tint = GrayColor6
                        )

                        BPMSpacer(width = 2.dp)
                    }
                }

                BPMSpacer(height = 10.dp)

                Text(
                    text = "한 줄 짜리 리뷰입니다.",
                    fontWeight = Normal,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 19.sp
                )

                BPMSpacer(height = 20.dp)

                LikeButton(
                    likeState = likeState,
                    onClick = { onClickLike() }
                )
            }

            BPMSpacer(height = 25.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ReviewDetailActivityContent {

    }
}