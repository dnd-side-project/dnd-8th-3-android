package com.d83t.bpm.presentation.ui.making_reservation

import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import java.time.DayOfWeek
import java.time.LocalDate

class MakingReservationActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val selectedDateState = mutableStateOf<LocalDate?>(null)

    override fun initUi() {
        setContent {
            BPMTheme {
                MakingReservationActivityContent(
                    selectedDateState = selectedDateState,
                    onClickSearchStudio = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private inline fun MakingReservationActivityContent(
    selectedDateState: MutableState<LocalDate?>,
    crossinline onClickSearchStudio: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
    ) {
        ScreenHeader(header = "일정 확정하기")

        MakingReservationItemLayout(
            isEssential = false,
            title = "스튜디오",
            expandedHeight = 124.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = GrayColor6
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .align(Center)
                        .clickableWithoutRipple { onClickSearchStudio() },
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "바디프로필 업체를 검색해보세요",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor5
                    )

                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "searchIcon"
                    )
                }
            }
        }

        Divider(color = GrayColor8)

        MakingReservationItemLayout(
            isEssential = true,
            title = if (selectedDateState.value != null) selectedDateState.value.toString().replace("-", ".") else "날짜",
            expandedHeight = 416.dp
        ) {
            val currentDate = LocalDate.now()
            val calendarState = remember { mutableStateOf(LocalDate.now()) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "${calendarState.value.month.name} ${calendarState.value.year} ",
                    fontFamily = pretendard,
                    fontWeight = SemiBold,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Row {
                    Icon(
                        modifier = Modifier
                            .size(10.dp)
                            .clickableWithoutRipple { calendarState.value = calendarState.value.minusMonths(1) },
                        painter = painterResource(id = R.drawable.ic_calendar_back),
                        contentDescription = "backIcon"
                    )

                    BPMSpacer(width = 30.dp)

                    Icon(
                        modifier = Modifier
                            .size(10.dp)
                            .clickableWithoutRipple { calendarState.value = calendarState.value.plusMonths(1) },
                        painter = painterResource(id = R.drawable.ic_calendar_forth),
                        contentDescription = "forthIcon"
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = SpaceEvenly,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    modifier = Modifier.size(40.dp),
                    text = "M",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "T",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "W",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "T",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "F",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "S",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )

                Text(
                    modifier = Modifier.size(40.dp),
                    text = "S",
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            }

            val dateArray = Array<LocalDate?>(42) { null }
            val indexOfFirstDayOfMonth = when (calendarState.value.withDayOfMonth(1).dayOfWeek) {
                DayOfWeek.MONDAY -> 0
                DayOfWeek.TUESDAY -> 1
                DayOfWeek.WEDNESDAY -> 2
                DayOfWeek.THURSDAY -> 3
                DayOfWeek.FRIDAY -> 4
                DayOfWeek.SATURDAY -> 5
                DayOfWeek.SUNDAY -> 6
                null -> 0
            }

            for (i in 0 until calendarState.value.lengthOfMonth()) {
                dateArray[indexOfFirstDayOfMonth + i] = calendarState.value.withDayOfMonth(1).plusDays(i.toLong())
            }

            repeat(6) { week ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = SpaceEvenly,
                    verticalAlignment = CenterVertically
                ) {
                    repeat(7) { day ->
                        val thisDay = dateArray[(7 * week) + day]
                        val dayBackgroundColorState = animateColorAsState(
                            targetValue = if (selectedDateState.value != null &&
                                selectedDateState.value == thisDay
                            ) Color.Black else Color.White
                        )
                        val dayTextColorState = animateColorAsState(
                            targetValue = if (selectedDateState.value != null &&
                                selectedDateState.value == thisDay
                            ) MainGreenColor else GrayColor2
                        )

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape)
                                .border(
                                    width = 1.dp,
                                    shape = CircleShape,
                                    color = if (currentDate == thisDay) MainGreenColor else Color.Transparent
                                )
                                .background(color = dayBackgroundColorState.value)
                                .clickableWithoutRipple {
                                    if (thisDay != null &&
                                        thisDay.toEpochDay() >= currentDate.toEpochDay()
                                    ) selectedDateState.value = thisDay
                                },
                        ) {
                            Text(
                                modifier = Modifier.align(Center),
                                text = if (thisDay != null) "${thisDay.dayOfMonth}" else "",
                                fontFamily = pretendard,
                                fontWeight = Medium,
                                fontSize = 14.sp,
                                letterSpacing = 0.sp,
                                color = if (thisDay != null &&
                                    thisDay.toEpochDay() < currentDate.toEpochDay()
                                ) GrayColor6
                                else dayTextColorState.value
                            )
                        }
                    }
                }
            }
        }

        Divider(color = GrayColor8)

        MakingReservationItemLayout(
            isEssential = false,
            title = "시간",
            expandedHeight = 206.dp
        ) {
            val hoursLazyListState = rememberLazyListState()
            val minutesLazyListState = rememberLazyListState()
            val timesLazyListState = rememberLazyListState()
            val hours = (0..13).toList()
            val minutes = (-1..60).toList()
            val times = listOf("", "오전", "오후", "")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Row(
                    modifier = Modifier.align(Center),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .width(80.dp)
                            .height(120.dp),
                        state = hoursLazyListState,
                        flingBehavior = rememberSnapperFlingBehavior(hoursLazyListState),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        items(hours) { hour ->
                            Box(modifier = Modifier.size(40.dp)) {
                                Text(
                                    modifier = Modifier.align(Center),
                                    text = if (hour in 1..12) "$hour" else "",
                                    fontFamily = pretendard,
                                    fontWeight = SemiBold,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.sp
                                )
                            }
                        }
                    }

                    Icon(
                        modifier = Modifier.align(CenterVertically),
                        painter = painterResource(id = R.drawable.ic_time_divider),
                        contentDescription = "timeDividerIcon"
                    )


                    LazyColumn(
                        modifier = Modifier
                            .width(80.dp)
                            .height(120.dp),
                        state = minutesLazyListState,
                        flingBehavior = rememberSnapperFlingBehavior(minutesLazyListState),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        items(minutes) { minute ->
                            Box(modifier = Modifier.size(40.dp)) {
                                Text(
                                    modifier = Modifier.align(Center),
                                    text = if (minute in 0..59) "$minute" else "",
                                    fontFamily = pretendard,
                                    fontWeight = SemiBold,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.sp
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .width(50.dp)
                            .height(120.dp),
                        state = timesLazyListState,
                        flingBehavior = rememberSnapperFlingBehavior(timesLazyListState),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        items(times) { times ->
                            Box(modifier = Modifier.size(40.dp)) {
                                Text(
                                    modifier = Modifier.align(Center),
                                    text = times,
                                    fontFamily = pretendard,
                                    fontWeight = SemiBold,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.sp
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    verticalArrangement = SpaceEvenly,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Divider(
                        modifier = Modifier.width(210.dp),
                        color = GrayColor8
                    )
                    Divider(
                        modifier = Modifier.width(210.dp),
                        color = GrayColor8
                    )
                }
            }
        }
    }
}

@Composable
private fun MakingReservationItemLayout(
    isEssential: Boolean,
    title: String,
    expandedHeight: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val showExpandedItemColumn = remember { mutableStateOf(false) }
    val columnHeightState = animateDpAsState(targetValue = if (showExpandedItemColumn.value) expandedHeight else 64.dp)
    val expandIconRotateState = animateFloatAsState(targetValue = if (showExpandedItemColumn.value) 180f else 0f)

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(columnHeightState.value)
    ) {
        BPMSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableWithoutRipple { showExpandedItemColumn.value = !showExpandedItemColumn.value },
            horizontalArrangement = SpaceBetween
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .height(24.dp)
                        .align(CenterVertically),
                    text = title,
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    letterSpacing = 0.sp
                )

                if (isEssential) {
                    Text(
                        text = "*",
                        fontFamily = pretendard,
                        fontWeight = Medium,
                        fontSize = 17.sp,
                        letterSpacing = 0.sp,
                        color = Color.Red
                    )
                }
            }

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(expandIconRotateState.value),
                painter = painterResource(id = R.drawable.ic_arrow_expand),
                contentDescription = "expandItemIcon"
            )
        }

        BPMSpacer(height = 20.dp)

        content()
    }
}

@Preview
@Composable
private fun Preview() {
//    MakingReservationActivityContent()
}