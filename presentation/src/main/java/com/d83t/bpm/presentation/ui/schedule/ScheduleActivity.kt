package com.d83t.bpm.presentation.ui.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.d83t.bpm.domain.model.Schedule
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.*
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.schedule.select_studio.SelectStudioActivity
import com.d83t.bpm.presentation.util.addFocusCleaner
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

@AndroidEntryPoint
class ScheduleActivity : BaseComponentActivity() {
    override val viewModel: ScheduleViewModel by viewModels()

    private lateinit var selectStudioLauncher: ActivityResultLauncher<Intent>

    private val editModeState = mutableStateOf(false)
    private val studioLabelTextState = mutableStateOf("???????????? ??????")
    private val selectedDateState = mutableStateOf<LocalDate?>(null)
    private val dateLabelTextState = mutableStateOf("??????")
    private val timeLabelTextState = mutableStateOf("??????")
    private val memoLabelTextState = mutableStateOf("??????")
    private val memoTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        selectStudioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                studioLabelTextState.value = it.data?.getStringExtra("studioName") ?: ""
            }
        }

        initComposeUi {
            ScheduleActivityContent(
                editModeState = editModeState,
                studioLabelTextState = studioLabelTextState,
                dateLabelTextState = dateLabelTextState,
                selectedDateState = selectedDateState,
                timeLabelTextState = timeLabelTextState,
                memoLabelTextState = memoLabelTextState,
                memoTextState = memoTextState,
                onClickSearchStudio = { selectStudioLauncher.launch(SelectStudioActivity.newIntent(this@ScheduleActivity)) },
                onClickSetTime = { timeText -> timeLabelTextState.value = timeText },
                onClickSave = { viewModel.onClickSave() }
            )
        }
    }

    override fun initUi() = Unit

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ScheduleState.Init -> Unit
                    is ScheduleState.Loading -> showLoadingScreen()
                    is ScheduleState.GetSuccess -> {
                        hideLoadingScreen()
                        initSchedule(state.schedule)
                    }
                    is ScheduleState.SaveSuccess -> {
                        hideLoadingScreen()
                        editModeState.value = false
                    }
                    is ScheduleState.Error -> hideLoadingScreen()
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    ScheduleViewEvent.Save -> {
                        viewModel.saveSchedule(
                            studioName = studioLabelTextState.value,
                            date = selectedDateState.value.toString(),
                            time = timeLabelTextState.value,
                            memo = memoTextState.value
                        )
                    }
                }
            }
        }
    }

    private fun initSchedule(
        schedule: Schedule
    ) {
        studioLabelTextState.value = schedule.studioName ?: ""
        dateLabelTextState.value = schedule.date ?: ""
        val timeInList = schedule.time?.split(":")
        timeLabelTextState.value =
            if (timeInList?.get(0)?.toInt()!! > 12) "${if (timeInList[0].toInt() - 12 < 10) "0" else ""}${timeInList[0].toInt() - 12}:${timeInList[1]} (??????)"
            else "${timeInList[0]}:${timeInList[1]} (??????)"
        val dateInList = schedule.date?.split("-")?.map { it.toInt() }
        selectedDateState.value = LocalDate.of(dateInList?.get(0)!!, dateInList[1], dateInList[2])
        memoLabelTextState.value = schedule.memo ?: ""
        memoTextState.value = schedule.memo ?: ""
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ScheduleActivity::class.java)
        }

    }
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private inline fun ScheduleActivityContent(
    editModeState: MutableState<Boolean>,
    studioLabelTextState: MutableState<String>,
    dateLabelTextState: MutableState<String>,
    selectedDateState: MutableState<LocalDate?>,
    timeLabelTextState: MutableState<String>,
    memoLabelTextState: MutableState<String>,
    memoTextState: MutableState<String>,
    crossinline onClickSearchStudio: () -> Unit,
    crossinline onClickSetTime: (String) -> Unit,
    crossinline onClickSave: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .windowInsetsPadding(insets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Vertical))
            .fillMaxSize()
            .imePadding()
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
            .addFocusCleaner(LocalFocusManager.current)
            .nestedScroll(connection = object : NestedScrollConnection {
                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    return Velocity(0f, available.y)
                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    return Offset(0f, available.y)
                }
            }),
        verticalArrangement = SpaceBetween
    ) {
        Column {
            ScreenHeader(
                header = if (editModeState.value) "?????? ????????????" else "??????",
                actionBlock = {
                    if (!editModeState.value) {
                        Text(
                            modifier = Modifier.clickableWithoutRipple { editModeState.value = true },
                            text = "??????",
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor5
                        )
                    }
                }
            )

            ScheduleItemLayout(
                editModeState = editModeState,
                isEssential = false,
                label = "???????????? ???????????????????",
                title = studioLabelTextState.value,
                expandedHeight = 135.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = GrayColor2
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 8.dp
                            )
                            .fillMaxWidth()
                            .align(Center)
                            .clickableWithoutRipple { onClickSearchStudio() },
                        horizontalArrangement = SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "??????????????? ????????? ??????????????????",
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor4
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

            ScheduleItemLayout(
                editModeState = editModeState,
                isEssential = true,
                label = "????????? ?????? ????????? ??????????????????",
                title = dateLabelTextState.value,
                expandedHeight = 439.dp
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
                        text = "${calendarState.value.year}??? " +
                                "${
                                    when (calendarState.value.month.name) {
                                        "JANUARY" -> "1"
                                        "FEBRUARY" -> "2"
                                        "MARCH" -> "3"
                                        "APRIL" -> "4"
                                        "MAY" -> "5"
                                        "JUNE" -> "6"
                                        "JULY" -> "7"
                                        "AUGUST" -> "8"
                                        "SEPTEMBER" -> "9"
                                        "OCTOBER" -> "10"
                                        "NOVEMBER" -> "11"
                                        else -> "12"
                                    }
                                }???",
                        fontWeight = SemiBold,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Row {
                        Icon(
                            modifier = Modifier
                                .size(10.dp)
                                .clickableWithoutRipple {
                                    calendarState.value = calendarState.value.minusMonths(1)
                                },
                            painter = painterResource(id = R.drawable.ic_calendar_back),
                            contentDescription = "backIcon"
                        )

                        BPMSpacer(width = 30.dp)

                        Icon(
                            modifier = Modifier
                                .size(10.dp)
                                .clickableWithoutRipple {
                                    calendarState.value = calendarState.value.plusMonths(1)
                                },
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
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )

                    Text(
                        modifier = Modifier.size(40.dp),
                        text = "???",
                        textAlign = TextAlign.Center,
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
                                        dateLabelTextState.value = thisDay.toString()
                                    },
                            ) {
                                Text(
                                    modifier = Modifier.align(Center),
                                    text = if (thisDay != null) "${thisDay.dayOfMonth}" else "",
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

            ScheduleItemLayout(
                editModeState = editModeState,
                isEssential = false,
                label = "????????? ????????? ??????????????????",
                title = timeLabelTextState.value,
                expandedHeight = 290.dp
            ) {
                val hoursLazyListState = rememberLazyListState()
                val minutesLazyListState = rememberLazyListState()
                val timeZonesLazyListState = rememberLazyListState()
                val hours = (0..13).toList()
                val minutes = (-1..60).toList()
                val timeZones = listOf("", "??????", "??????", "")
                val scope = rememberCoroutineScope()

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
                            itemsIndexed(hours) { index, hour ->
                                Box(modifier = Modifier.size(40.dp)) {
                                    val textColorState = animateColorAsState(targetValue = if (index == remember { derivedStateOf { hoursLazyListState.firstVisibleItemIndex } }.value + 1) Color.Black else GrayColor5)

                                    Text(
                                        modifier = Modifier
                                            .align(Center)
                                            .clickableWithoutRipple {
                                                scope.launch {
                                                    if (index != 0) hoursLazyListState.animateScrollToItem(
                                                        index - 1
                                                    )
                                                }
                                            },
                                        text = if (hour in 1..12) String.format("%02d", hour) else if (hour == 0) "???" else "",
                                        fontWeight = SemiBold,
                                        fontSize = 14.sp,
                                        letterSpacing = 0.sp,
                                        color = textColorState.value
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
                            itemsIndexed(minutes) { index, minute ->
                                val textColorState = animateColorAsState(targetValue = if (index == remember { derivedStateOf { minutesLazyListState.firstVisibleItemIndex } }.value + 1) Color.Black else GrayColor5)

                                Box(modifier = Modifier.size(40.dp)) {
                                    Text(
                                        modifier = Modifier
                                            .align(Center)
                                            .clickableWithoutRipple {
                                                scope.launch {
                                                    if (index != 0) minutesLazyListState.animateScrollToItem(
                                                        index - 1
                                                    )
                                                }
                                            },
                                        text = if (minute in 0..59) String.format("%02d", minute) else if (minute == -1) "???" else "",
                                        fontWeight = SemiBold,
                                        fontSize = 14.sp,
                                        letterSpacing = 0.sp,
                                        color = textColorState.value
                                    )
                                }
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .width(50.dp)
                                .height(120.dp),
                            state = timeZonesLazyListState,
                            flingBehavior = rememberSnapperFlingBehavior(timeZonesLazyListState),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            itemsIndexed(timeZones) { index, times ->
                                val textColorState = animateColorAsState(targetValue = if (index == remember { derivedStateOf { timeZonesLazyListState.firstVisibleItemIndex } }.value + 1) Color.Black else GrayColor5)

                                Box(modifier = Modifier.size(40.dp)) {
                                    Text(
                                        modifier = Modifier
                                            .align(Center)
                                            .clickableWithoutRipple {
                                                scope.launch {
                                                    if (index != 0) timeZonesLazyListState.animateScrollToItem(
                                                        index - 1
                                                    )
                                                }
                                            },
                                        text = times,
                                        fontWeight = SemiBold,
                                        fontSize = 14.sp,
                                        letterSpacing = 0.sp,
                                        color = textColorState.value
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

                BPMSpacer(height = 30.dp)

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .height(42.dp)
                        .width(210.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = GrayColor5
                        )
                        .align(CenterHorizontally)
                        .clickable {
                            onClickSetTime(
                                "${
                                    String.format(
                                        "%02d",
                                        hours[hoursLazyListState.firstVisibleItemIndex + 1]
                                    )
                                }:" +
                                        "${
                                            String.format(
                                                "%02d",
                                                minutes[minutesLazyListState.firstVisibleItemIndex + 1]
                                            )
                                        } " +
                                        "(${timeZones[timeZonesLazyListState.firstVisibleItemIndex + 1]})"
                            )
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Center),
                        text = "??????",
                        fontWeight = SemiBold,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor3
                    )
                }
            }

            Divider(color = GrayColor8)

            ScheduleItemLayout(
                editModeState = editModeState,
                isEssential = false,
                label = "?????? ?????? ???????????? ????????? ???????????????",
                title = memoLabelTextState.value,
                expandedHeight = 205.dp
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(6.dp),
                            color = if (memoTextState.value.isNotEmpty()) GrayColor1 else GrayColor6
                        )
                        .fillMaxWidth()
                        .height(110.dp)
                ) {
                    TextFieldColorProvider {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            value = memoTextState.value,
                            placeholder = {
                                Text(
                                    text = "????????? ?????? ????????? ??????????????????.",
                                    fontWeight = Normal,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.sp,
                                    color = GrayColor6
                                )
                            },
                            onValueChange = {
                                memoTextState.value = it
                                memoLabelTextState.value = it
                            },
                            textStyle = TextStyle(
                                fontWeight = Normal,
                                fontSize = 13.sp,
                                letterSpacing = 0.sp,
                                color = Color.Black
                            ),
                            colors = textFieldColors()
                        )
                    }
                }
            }

            Divider(color = GrayColor8)
        }

        Column {
            BPMSpacer(height = 20.dp)

            if (editModeState.value) {
                RoundedCornerButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    text = "????????????",
                    textColor = if (selectedDateState.value != null) Color.Black else GrayColor7,
                    buttonColor = if (selectedDateState.value != null) MainGreenColor else GrayColor9,
                    onClick = { if (selectedDateState.value != null) onClickSave() }
                )
            }

            BPMSpacer(height = 20.dp)
        }
    }
}

@Composable
private fun ScheduleItemLayout(
    editModeState: MutableState<Boolean>,
    isEssential: Boolean,
    label: String,
    title: String,
    expandedHeight: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val expandState = remember { mutableStateOf(false) }
    val columnHeightState = animateDpAsState(targetValue = if (expandState.value) expandedHeight else 77.dp)
    val focusManager = LocalFocusManager.current

    if (!editModeState.value) {
        focusManager.clearFocus()
        expandState.value = false
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(columnHeightState.value)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .clickableWithoutRipple {
                    if (editModeState.value) {
                        expandState.value = !expandState.value
                        focusManager.clearFocus()
                    }
                }
        ) {
            Column(modifier = Modifier.align(Center)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row {
                        Text(
                            text = label,
                            fontWeight = SemiBold,
                            fontSize = 12.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor5
                        )

                        if (isEssential) {
                            Text(
                                text = "*",
                                fontWeight = SemiBold,
                                fontSize = 12.sp,
                                letterSpacing = 0.sp,
                                color = Color.Red
                            )
                        }
                    }

                    Box(modifier = Modifier.size(22.dp)) {
                        if (editModeState.value) {
                            Icon(
                                modifier = Modifier
                                    .size(22.dp)
                                    .rotate(if (expandState.value) 180f else 0f),
                                painter = painterResource(id = R.drawable.ic_arrow_expand_0),
                                contentDescription = "expandItemIcon",
                                tint = if (expandState.value) GrayColor2 else GrayColor5
                            )
                        }
                    }
                }

                Row(modifier = Modifier.padding(end = 36.dp)) {
                    Text(
                        modifier = Modifier
                            .height(24.dp)
                            .align(CenterVertically),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 17.sp,
                        letterSpacing = 0.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (isEssential) {
                        Text(
                            text = "*",
                            fontWeight = Medium,
                            fontSize = 17.sp,
                            letterSpacing = 0.sp,
                            color = Color.Red
                        )
                    }
                }
            }
        }

        BPMSpacer(height = 1.dp)

        if (!expandState.value) {
            Box {
                Column {
                    content()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableWithoutRipple { }
                )
            }
        } else {
            content()
        }
    }
}
