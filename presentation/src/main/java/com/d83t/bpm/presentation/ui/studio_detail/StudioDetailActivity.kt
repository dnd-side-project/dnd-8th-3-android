package com.d83t.bpm.presentation.ui.studio_detail

import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.*
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

    private val studioLikeState by lazy { mutableStateOf(false) }

    override fun initUi() {
        setContent {
            BPMTheme {
                StudioDetailActivityContent(
                    studioLikeState = studioLikeState,
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

    override fun setupCollect() = Unit
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private inline fun StudioDetailActivityContent(
    studioLikeState: MutableState<Boolean>,
    crossinline onClickCallButton: () -> Unit,
    crossinline onClickInfoEditSuggestion: () -> Unit,
    crossinline onClickMap: () -> Unit,
    crossinline onClickCopyAddress: () -> Unit,
    crossinline onClickShowCourse: () -> Unit
) {
    val scrollState = rememberScrollState()
    val tabState = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val showExpandedKeywordColumn = remember { mutableStateOf(false) }
    val keywordColumnHeightState = animateDpAsState(targetValue = if (showExpandedKeywordColumn.value) 474.dp else 138.dp)
    val expandIconRotateState = animateFloatAsState(targetValue = if (showExpandedKeywordColumn.value) 180f else 0f)
    val showImageReviewOnly = remember { mutableStateOf(false) }
    val showReviewOrderByLike = remember { mutableStateOf(true) }
    val studioDetailInfoHeightState = remember { mutableStateOf(1) }
    val reviewHeaderPositionState = remember { mutableStateOf(0f) }

    tabState.value = if (remember { derivedStateOf { scrollState.value >= studioDetailInfoHeightState.value } }.value) 1 else 0

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
                            .align(BottomStart)
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

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Row(verticalAlignment = CenterVertically) {
                            Text(
                                text = "서울",
                                fontWeight = Normal,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = "tagDepthIcon"
                            )

                            Text(
                                text = "서초구",
                                fontWeight = Normal,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp
                            )
                        }

                        BPMSpacer(height = 8.dp)

                        Text(
                            text = "스튜디오 이름",
                            fontWeight = SemiBold,
                            fontSize = 19.sp,
                            letterSpacing = 0.sp
                        )

                        BPMSpacer(height = 6.dp)

                        Text(
                            text = "스튜디오에 대한 간단한 한줄 설명을 붙여주세요.",
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
                                    fontSize = 14.sp,
                                    fontWeight = Normal,
                                    letterSpacing = 0.sp,
                                    color = GrayColor3
                                )
                            }

                            Text(
                                text = "후기 504개",
                                fontWeight = Normal,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp,
                                style = TextStyle(textDecoration = TextDecoration.Underline)
                            )
                        }
                    }

                    Image(
                        modifier = Modifier
                            .size(22.dp)
                            .align(TopEnd)
                            .clickableWithoutRipple { studioLikeState.value = !studioLikeState.value },
                        painter = painterResource(id = if (studioLikeState.value) R.drawable.ic_heart_active else R.drawable.ic_heart_inactive),
                        contentDescription = "heartImage"
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
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.clickableWithoutRipple { onClickInfoEditSuggestion() },
                        text = "정보수정 제안",
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
                        fontWeight = Medium,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    BPMSpacer(height = 8.dp)

                    Text(
                        text = "상세 주소에 대한 설명을 적는 곳입니다.",
                        fontWeight = Normal,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )

                    BPMSpacer(height = 12.dp)

                    Box {
                        AndroidView(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(12.dp))
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

                        Column(
                            modifier = Modifier
                                .height(90.dp)
                                .align(TopCenter),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(36.dp)
                                    .align(CenterHorizontally),
                                painter = painterResource(id = R.drawable.ic_marker),
                                contentDescription = "mapMarkerIcon"
                            )
                        }

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

                BPMSpacer(height = 25.dp)

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
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        text = "마지막 업데이트 : 0000 00 00",
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

                BPMSpacer(height = 25.dp)

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
                        .clip(shape = RoundedCornerShape(12.dp))
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

                BPMSpacer(height = 25.dp)

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
                        .height(55.dp)
                        .onGloballyPositioned { coordinates -> reviewHeaderPositionState.value = coordinates.positionInWindow().y },
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "업체 리뷰 120",
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        text = "리뷰 작성하기",
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
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )
                    }

                    Row {
                        Text(
                            modifier = Modifier.clickableWithoutRipple { showReviewOrderByLike.value = true },
                            text = "좋아요순",
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
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = if (showReviewOrderByLike.value) GrayColor6 else Color.Black
                        )
                    }
                }

                Divider(color = GrayColor13)

                Box {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        listOf(true, false, true, true, false).forEach { review ->
                            ReviewComposable(review)
                        } // dummy
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color(0X53FFFFFF),
                                        Color(0X73FFFFFF),
                                        Color(0XA2FFFFFF),
                                        Color(0XD9FFFFFF),
                                        Color(0XF2FFFFFF),
                                    )
                                )
                            )
                            .align(BottomCenter)
                    ) {
                        RoundedCornerButton(
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp
                                )
                                .fillMaxWidth()
                                .height(48.dp)
                                .align(BottomCenter),
                            text = "더보기",
                            textColor = Color.White,
                            buttonColor = Color.Black,
                            onClick = {}
                        )
                    }
                }
            }
        }

        Column {
            ScreenHeader("스튜디오 이름")

            Row(modifier = Modifier.fillMaxWidth()) {
                StudioDetailTab(
                    modifier = Modifier.weight(1f),
                    text = "상품설명",
                    tabIndex = 0,
                    tabState = tabState,
                    onClick = {
                        tabState.value = 0
                        scope.launch { scrollState.animateScrollTo(0) }
                    }
                )

                StudioDetailTab(
                    modifier = Modifier.weight(1f),
                    text = "리뷰",
                    tabIndex = 1,
                    tabState = tabState,
                    onClick = {
                        tabState.value = 1
                        scope.launch { scrollState.animateScrollTo(studioDetailInfoHeightState.value) }
                    }
                )
            }
        }

        with(LocalDensity.current) {
            val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
            if (remember { derivedStateOf { reviewHeaderPositionState.value >= screenHeightDp.toPx() } }.value) {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 25.dp,
                            end = 16.dp
                        )
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(shape = RoundedCornerShape(50.dp))
                        .border(
                            width = 1.dp,
                            color = GrayColor3,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .height(36.dp)
                        .background(color = Color.White)
                        .align(BottomEnd)
                        .clickable {
                            scope.launch {
                                tabState.value = 1
                                scrollState.animateScrollTo(studioDetailInfoHeightState.value)
                            }
                        },
                ) {
                    Row(
                        modifier = Modifier.align(Center),
                        horizontalArrangement = spacedBy(4.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        BPMSpacer(width = 14.dp)

                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = "리뷰 바로 작성하기",
                            fontWeight = SemiBold,
                            fontSize = 12.sp,
                            letterSpacing = 0.sp,
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_down_small),
                            contentDescription = "downToReviewIcon",
                            tint = GrayColor6
                        )

                        BPMSpacer(width = 14.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun StudioDetailTab(
    modifier: Modifier,
    text: String,
    tabIndex: Int,
    tabState: MutableState<Int>,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Center),
            text = text,
            fontWeight = SemiBold,
            fontSize = 15.sp,
            letterSpacing = 0.sp,
            color = if (tabState.value == tabIndex) Color.Black else GrayColor6
        )

        Divider(
            modifier = Modifier.align(BottomCenter),
            thickness = 2.dp,
            color = if (tabState.value == tabIndex) Color.Black else Color.White
        )
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
            fontWeight = Medium,
            fontSize = 13.sp,
            letterSpacing = 0.sp
        )

        Text(
            text = detail,
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
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.sp,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    StudioDetailActivityContent(
        studioLikeState = remember { mutableStateOf(false) },
        onClickCallButton = { /*TODO*/ },
        onClickInfoEditSuggestion = { /*TODO*/ },
        onClickMap = { /*TODO*/ },
        onClickCopyAddress = { /*TODO*/ }) {

    }
}