package com.d83t.bpm.presentation.ui.studio_detail

import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.OutLinedRoundedCornerButton
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class StudioDetailActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun initUi() {
        setContent {
            BPMTheme {
                StudioDetailActivityContent(
                    onClickCallButton = {

                    },
                    onClickInfoEditSuggestion = {

                    },
                    onClickMap = {

                    },
                    onClickCopyAddress = {

                    },
                    onClickShowCourse = {

                    }
                )
            }
        }
    }

    override fun setupCollect() {

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private inline fun StudioDetailActivityContent(
    crossinline onClickCallButton: () -> Unit,
    crossinline onClickInfoEditSuggestion: () -> Unit,
    crossinline onClickMap: () -> Unit,
    crossinline onClickCopyAddress: () -> Unit,
    crossinline onClickShowCourse: () -> Unit
) {
    val scrollState = rememberScrollState()
    val tabState = remember { mutableStateOf(0) }
    val tabs = listOf("상품설명", "리뷰")
    val scope = rememberCoroutineScope()
    val showExpandedKeywordColumn = remember { mutableStateOf(false) }
    val keywordColumnHeightState = animateDpAsState(targetValue = if (showExpandedKeywordColumn.value) 474.dp else 138.dp)
    val expandIconRotateState = animateFloatAsState(targetValue = if (showExpandedKeywordColumn.value) 180f else 0f)
    val showImageReviewOnly = remember { mutableStateOf(false) }
    val showReviewOrderByLike = remember { mutableStateOf(true) }
    val studioDetailInfoHeightState = remember { mutableStateOf(0) }

    tabState.value = if (remember { derivedStateOf { scrollState.value > studioDetailInfoHeightState.value } }.value) 1 else 0

    Box(modifier = Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .padding(top = 95.dp)
                .verticalScroll(state = scrollState),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates -> studioDetailInfoHeightState.value = coordinates.size.height }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        count = 1
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
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
                                text = "서울",
                                style = BPMTypography.body2
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = "tagDepthIcon"
                            )

                            Text(
                                text = "서초구",
                                style = BPMTypography.body2
                            )
                        }

                        BPMSpacer(height = 8.dp)

                        Text(
                            text = "스튜디오 이름",
                            style = BPMTypography.h3
                        )

                        BPMSpacer(height = 6.dp)

                        Text(
                            text = "스튜디오에 대한 간단한 한줄 설명을 붙여주세요.",
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
                                    text = "4.0",
                                    fontFamily = pretendard,
                                    fontSize = 14.sp,
                                    fontWeight = Normal,
                                    letterSpacing = 0.sp,
                                    color = GrayColor3
                                )
                            }

                            Text(
                                text = "후기 504개",
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
                    onClick = { onClickCallButton() }
                )

                BPMSpacer(height = 36.dp)

                Divider(
                    thickness = 8.dp,
                    color = GrayColor11
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "위치 정보",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.clickable { onClickInfoEditSuggestion() },
                        text = "정보수정 제안",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )
                }

                Divider(color = GrayColor8)

                BPMSpacer(height = 24.dp)

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "주소에 대한 정보를 적습니다.",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    BPMSpacer(height = 8.dp)

                    Text(
                        text = "상세 주소에 대한 설명을 적는 곳입니다.",
                        fontFamily = pretendard,
                        fontWeight = Normal,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )

                    BPMSpacer(height = 12.dp)

                    Box {
                        AndroidView(
                            modifier = Modifier
                                .clip(shape = BPMShapes.large)
                                .fillMaxWidth()
                                .height(180.dp),
                            factory = { context ->
                                MapView(context).apply {
                                    setMapCenterPoint(
                                        MapPoint.mapPointWithGeoCoord(
                                            35.8589,
                                            128.4988
                                        ), false
                                    )
                                }
                            }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clickableWithoutRipple { onClickMap() }
                        )
                    }

                    BPMSpacer(height = 12.dp)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutLinedRoundedCornerButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            text = "주소 복사",
                            textColor = Color.Black,
                            buttonColor = Color.White,
                            outLineColor = GrayColor6,
                            onClick = { onClickCopyAddress() }
                        )

                        BPMSpacer(width = 8.dp)

                        RoundedCornerButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            text = "길찾기",
                            textColor = Color.Black,
                            buttonColor = SubGreenColor,
                            onClick = { onClickShowCourse() }
                        )
                    }
                }

                BPMSpacer(height = 24.dp)

                Divider(
                    thickness = 8.dp,
                    color = GrayColor11
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "편의 정보",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        text = "마지막 업데이트 : 0000 00 00",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor5
                    )
                }

                Divider(color = GrayColor13)

                BPMSpacer(height = 24.dp)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    ConvenienceInfo(
                        type = "전화번호",
                        detail = "전화번호에 대한 설명을 적는 곳입니다."
                    )

                    BPMSpacer(height = 12.dp)

                    ConvenienceInfo(
                        type = "SNS",
                        detail = "SNS에 대한 설명을 적는 곳입니다."
                    )

                    BPMSpacer(height = 12.dp)

                    ConvenienceInfo(
                        type = "영업시간",
                        detail = "영업시간에 대한 설명을 적는 곳입니다."
                    )

                    BPMSpacer(height = 12.dp)

                    ConvenienceInfo(
                        type = "가격정보",
                        detail = "가격정보에 대한 설명을 적는 곳입니다."
                    )
                }

                BPMSpacer(height = 24.dp)

                Divider(
                    thickness = 8.dp,
                    color = GrayColor11
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(CenterStart),
                        text = "이런 점을 추천해요",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Divider(color = GrayColor13)

                BPMSpacer(height = 24.dp)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(shape = BPMShapes.large)
                        .fillMaxWidth()
                        .background(color = GrayColor10)
                ) {
                    BPMSpacer(height = 15.dp)

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(keywordColumnHeightState.value)
                    ) {
                        repeat(10) { number ->
                            BestKeyword(
                                number = number + 1,
                                keyword = "친절해요",
                                count = 28,
                                backgroundColor = GrayColor2,
                                textColor = Color.White
                            )

                            if (number != 9) {
                                BPMSpacer(height = 6.dp)
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp)
                                .clickableWithoutRipple { showExpandedKeywordColumn.value = !showExpandedKeywordColumn.value }
                                .align(Center)
                                .rotate(expandIconRotateState.value),
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = "expandColumnIcon",
                            tint = GrayColor5
                        )
                    }
                }

                BPMSpacer(height = 24.dp)

                Divider(
                    thickness = 8.dp,
                    color = GrayColor11
                )
            }

            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "업체 리뷰 120",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        text = "후기 작성하기",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )
                }

                Divider(color = GrayColor13)

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row(modifier = Modifier.clickableWithoutRipple { showImageReviewOnly.value = !showImageReviewOnly.value }) {
                        Icon(
                            modifier = Modifier.align(CenterVertically),
                            painter = painterResource(id = R.drawable.ic_check_field),
                            contentDescription = "checkFieldIcon",
                            tint = if (showImageReviewOnly.value) Color.Black else GrayColor7
                        )

                        BPMSpacer(width = 8.dp)

                        Text(
                            text = "사진 리뷰만 보기",
                            fontFamily = pretendard,
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )
                    }

                    Row {
                        Text(
                            modifier = Modifier.clickableWithoutRipple { showReviewOrderByLike.value = true },
                            text = "좋아요순",
                            fontFamily = pretendard,
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = if (showReviewOrderByLike.value) Color.Black else GrayColor6
                        )

                        BPMSpacer(width = 20.dp)

                        Divider(
                            modifier = Modifier
                                .height(12.dp)
                                .width(1.dp)
                                .align(CenterVertically),
                            color = GrayColor3
                        )

                        BPMSpacer(width = 20.dp)

                        Text(
                            modifier = Modifier.clickableWithoutRipple { showReviewOrderByLike.value = false },
                            text = "최신순",
                            fontFamily = pretendard,
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = if (showReviewOrderByLike.value) GrayColor6 else Color.Black
                        )
                    }
                }

                Divider(color = GrayColor13)

                listOf(true, false, true, true, false).forEach { review ->
                    Review(review)
                } // dummy
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
                            scope.launch { scrollState.animateScrollTo(if (index == 0) 0 else studioDetailInfoHeightState.value) }
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = GrayColor6
                    )
                }
            }
        }
    }
}

@Composable
private fun ConvenienceInfo(
    type: String,
    detail: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = type,
            fontFamily = pretendard,
            fontWeight = Medium,
            fontSize = 13.sp,
            letterSpacing = 0.sp
        )

        Text(
            text = detail,
            fontFamily = pretendard,
            fontWeight = Normal,
            fontSize = 12.sp,
            letterSpacing = 0.sp,
            color = GrayColor3
        )
    }
}

@Composable
private fun BestKeyword(
    number: Int,
    keyword: String,
    count: Int,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .fillMaxWidth()
            .height(42.dp)
            .background(color = backgroundColor),
        horizontalArrangement = SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Row(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${number}위",
                fontFamily = pyeongchang,
                fontWeight = Bold,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = textColor
            )

            BPMSpacer(width = 6.dp)

            Text(
                text = keyword,
                fontFamily = pyeongchang,
                fontWeight = Normal,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = textColor
            )
        }

        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = "+${count}",
            fontFamily = pretendard,
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.sp,
            color = textColor
        )
    }
}

@Composable
private fun Review(
    liked: Boolean
) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            BPMSpacer(height = 15.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row(verticalAlignment = CenterVertically) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.default_profile_image),
                        contentDescription = "profileImage"
                    )

                    BPMSpacer(width = 8.dp)

                    Text(
                        text = "닉네임",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )
                }

                Text(
                    text = "2023.02.15",
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }

            BPMSpacer(height = 12.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "스튜디오 이름",
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

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
            }

            Row(modifier = Modifier.padding(vertical = 14.dp)) {
                repeat(5) { index ->
                    Image(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        painter = painterResource(id = R.drawable.dummy_studio),
                        contentDescription = "reviewImage",
                        contentScale = Crop
                    )

                    if (index != 4) {
                        BPMSpacer(width = 4.dp)
                    }
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "One, two, three, four Baby, got me looking so crazy 빠져버리는 daydream got me feeling you 너도 말해줄래 누가 내게 뭐라든 남들과는 달라 넌 Maybe you could be the one 날 믿어봐 한 번 I'm not looking for just fun Maybe I could be the one Oh baby 예민하대 나 lately 너 없이는 나 매일매일이 yeah 재미없어 어쩌지 I just want you Call my phone right now I just wanna hear you're mine",
                fontFamily = pretendard,
                fontWeight = Normal,
                fontSize = 13.sp,
                letterSpacing = 0.sp,
                maxLines = 4,
                lineHeight = 19.sp,
                overflow = Ellipsis
            )

            BPMSpacer(height = 25.dp)

            Box(
                modifier = Modifier
                    .clip(shape = BPMShapes.large)
                    .height(28.dp)
                    .border(
                        width = 1.dp,
                        color = if (liked) Color.Black else GrayColor9,
                        shape = BPMShapes.large
                    )
                    .background(color = if (liked) Color.Black else Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Center),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "likeIcon",
                        tint = if (liked) MainGreenColor else Color.Black
                    )

                    BPMSpacer(width = 4.dp)

                    Text(
                        text = "좋아요",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = if (liked) MainGreenColor else Color.Black
                    )

                    BPMSpacer(width = 4.dp)

                    Text(
                        text = "12",
                        fontFamily = pretendard,
                        fontWeight = SemiBold,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = if (liked) MainGreenColor else Color.Black
                    )
                }
            }
        }

        BPMSpacer(height = 20.dp)

        Divider(color = GrayColor13)
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    StudioDetailActivityContent(
        onClickCallButton = { /*TODO*/ },
        onClickInfoEditSuggestion = { /*TODO*/ },
        onClickMap = { /*TODO*/ },
        onClickCopyAddress = { /*TODO*/ }) {

    }
}