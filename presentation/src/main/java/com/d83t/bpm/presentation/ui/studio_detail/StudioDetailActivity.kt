package com.d83t.bpm.presentation.ui.studio_detail

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudioDetailActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun initUi() {
        setContent {
            BPMTheme {
                StudioDetailActivityContent(
                    onClickCallButton = {

                    }
                )
            }
        }
    }

    override fun setupCollect() {
        TODO("Not yet implemented")
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private inline fun StudioDetailActivityContent(
    crossinline onClickCallButton: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val tabState = remember { mutableStateOf(0) }
    val tabs = listOf("상품설명", "리뷰")
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.background(color = Color.White)) {
        LazyColumn(modifier = Modifier.padding(top = 95.dp)) {
            item {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        HorizontalPager(
                            modifier = Modifier.fillParentMaxSize(),
                            count = 1
                        ) { // TODO : Will be changed
                            Image(
                                modifier = Modifier.fillParentMaxSize(),
                                painter = painterResource(id = R.drawable.dummy_studio),
                                contentDescription = "studioImage",
                                contentScale = ContentScale.Fit
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
                                .background(color = FilteredGrayColor)
                                .align(BottomStart)
                        ) {
                            Text(
                                modifier = Modifier.align(Center),
                                text = "1/1",
                                style = BPMTypography.subtitle2
                            )
                        }
                    }

                    BPMSpacer(height = 20.dp)

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            Row(verticalAlignment = CenterVertically) {
                                Text(
                                    text = "서울", // TODO : Will be changed
                                    style = BPMTypography.body2
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_right),
                                    contentDescription = "tagDepthIcon"
                                )

                                Text(
                                    text = "서초구", // TODO : Will be changed
                                    style = BPMTypography.body2
                                )
                            }

                            BPMSpacer(height = 8.dp)

                            Text(
                                text = "스튜디오 이름", // TODO : Will be changed
                                style = BPMTypography.h3
                            )

                            BPMSpacer(height = 6.dp)

                            Text(
                                text = "스튜디오에 대한 간단한 한줄 설명을 붙여주세요.", // TODO : Will be changed
                                fontFamily = pretendard,
                                fontSize = 13.sp,
                                fontWeight = Normal,
                                letterSpacing = 0.sp,
                                color = GrayColor3
                            )

                            BPMSpacer(height = 8.dp)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = SpaceBetween
                            ) {
                                Row {
                                    repeat(5) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_star_small),
                                            contentDescription = "starIcon",
                                            tint = GrayColor6
                                        )

                                        BPMSpacer(width = 2.dp)
                                    }

                                    BPMSpacer(width = 8.dp)

                                    Text(
                                        text = "4.0", // TODO : Will be changed
                                        fontFamily = pretendard,
                                        fontSize = 14.sp,
                                        fontWeight = Normal,
                                        letterSpacing = 0.sp,
                                        color = GrayColor3
                                    )
                                }

                                Text(
                                    text = "후기 504개", // TODO : Will be changed
                                    fontFamily = pretendard,
                                    fontWeight = Normal,
                                    fontSize = 12.sp,
                                    letterSpacing = 0.sp,
                                    style = TextStyle(textDecoration = TextDecoration.Underline)
                                )
                            }
                        }

                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(TopEnd),
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "heartIcon",
                            tint = GrayColor8
                        )
                    }

                    BPMSpacer(height = 36.dp)

                    RoundedCornerButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(48.dp),
                        text = "전화 걸기",
                        textColor = Color.White,
                        buttonColor = Color.Black,
                        onClick = {
                            onClickCallButton()
                        }
                    )

                    BPMSpacer(height = 36.dp)
                }
            }
        }

        Column {
            ScreenHeader("스튜디오 이름")

            TabRow(
                modifier = Modifier.height(40.dp),
                selectedTabIndex = tabState.value,
                backgroundColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabState.value]),
                        color = Color.Black,
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, tabName ->
                    Tab(
                        text = {
                            Text(
                                text = tabName,
                                style = BPMTypography.subtitle1
                            )
                        },
                        selected = tabState.value == index,
                        onClick = {
                            tabState.value = index
                            scope.launch { lazyListState.animateScrollToItem(index) }
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = GrayColor6
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    StudioDetailActivityContent(
        onClickCallButton = {

        }
    )
}