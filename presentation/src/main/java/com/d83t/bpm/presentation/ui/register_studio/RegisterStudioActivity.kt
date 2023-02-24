package com.d83t.bpm.presentation.ui.register_studio

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.KeywordChip
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.addFocusCleaner
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val dummyKeywordChipList = listOf(
    "친절해요",
    "소통이 빨라요",
    "소품이 다양해요",
    "세트장 구성이 다양해요",
    "제공하는 컨셉이 다양해요",
    "자연스럽게 연출해줘요",
    "시설이 깔끔해요",
    "원하는 스타일을 바로 파악해줘요",
    "주차하기 편해요",
    "보정을 꼼꼼하게 해줘요",
    "가격이 합리적이에요",
    "파우더룸이 잘 되어있어요",
    "요청사항을 잘 들어주세요",
    "스튜디오가 넓어요",
    "여유롭게 준비할 수 있어요"
)

class RegisterStudioActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val studioNameTextState = mutableStateOf("")
    private val studioLocationTextState = mutableStateOf("")
    private val phoneNumberTextState = mutableStateOf("")
    private val snsAddressTextState = mutableStateOf("")
    private val businessHoursTextState = mutableStateOf("")
    private val priceInfoTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun initUi() {
        setContent {
            BPMTheme {
                RegisterStudioActivityContent(
                    studioNameTextState = studioNameTextState,
                    studioLocationTextState = studioLocationTextState,
                    phoneNumberTextState = phoneNumberTextState,
                    snsAddressTextState = snsAddressTextState,
                    businessHoursTextState = businessHoursTextState,
                    priceInfoTextState = priceInfoTextState,
                    onClickLocation = {

                    },
                    onClickSave = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private fun RegisterStudioActivityContent(
    studioNameTextState: MutableState<String>,
    studioLocationTextState: MutableState<String>,
    phoneNumberTextState: MutableState<String>,
    snsAddressTextState: MutableState<String>,
    businessHoursTextState: MutableState<String>,
    priceInfoTextState: MutableState<String>,
    onClickSave: () -> Unit,
    onClickLocation: () -> Unit
) {
    val scrollState = rememberScrollState()
    val detailInfoExpandState = remember { mutableStateOf(false) }
    val detailInfoColumnHeightState = animateDpAsState(targetValue = if (detailInfoExpandState.value) 500.dp else 64.dp)
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .windowInsetsPadding(insets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Vertical))
            .imePadding()
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
            .addFocusCleaner(focusManager = focusManager)
    ) {
        ScreenHeader(header = "새 업체 등록하기")

        BPMSpacer(height = 30.dp)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            RegisterTextField(
                title = "업체 이름",
                textState = studioNameTextState,
                singleLine = true
            )

            BPMSpacer(height = 25.dp)

            Text(
                text = "위치",
                fontWeight = SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                color = GrayColor3
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                        .align(BottomCenter),
                    text = studioLocationTextState.value,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    color = Color.Black
                )

                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(TopEnd)
                        .clickableWithoutRipple { onClickLocation() },
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "studioLocationIcon",
                    tint = GrayColor6
                )

                Divider(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .align(BottomCenter),
                    color = GrayColor6,
                    thickness = 1.dp
                )
            }
        }

        BPMSpacer(height = 30.dp)

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        BPMSpacer(height = 34.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "이런 점을 추천해요",
                fontWeight = SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )

            BPMSpacer(height = 8.dp)

            Text(
                text = "최대 5개까지 선택가능해요",
                fontWeight = Medium,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = GrayColor4
            )
        }

        BPMSpacer(height = 14.dp)

        Divider(color = GrayColor13)

        BPMSpacer(height = 20.dp)

        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            mainAxisSpacing = 7.dp,
            crossAxisSpacing = 12.dp
        ) {
            dummyKeywordChipList.forEach { dummyKeyword ->
                KeywordChip(text = dummyKeyword)
            }
        }

        BPMSpacer(height = 30.dp)

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        BPMSpacer(height = 14.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(detailInfoColumnHeightState.value)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .align(Center)
                        .clickableWithoutRipple {
                            detailInfoExpandState.value = !detailInfoExpandState.value
                            if (detailInfoExpandState.value) {
                                scope.launch {
                                    delay(300L)
                                    scrollState.animateScrollTo(scrollState.maxValue)
                                }
                            }
                        },
                    horizontalArrangement = SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "추가정보",
                        fontWeight = Medium,
                        fontSize = 17.sp,
                        letterSpacing = 0.sp
                    )

                    Icon(
                        modifier = Modifier.rotate(if (detailInfoExpandState.value) 0f else 180f),
                        painter = painterResource(id = R.drawable.ic_arrow_expand_1),
                        contentDescription = "expandColumnIcon",
                        tint = GrayColor5
                    )
                }

                Divider(
                    modifier = Modifier.align(BottomCenter),
                    color = GrayColor8
                )
            }

            Box {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    BPMSpacer(height = 36.dp)

                    RegisterTextField(
                        title = "전화번호",
                        textState = phoneNumberTextState,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Next) })
                    )

                    BPMSpacer(height = 24.dp)

                    RegisterTextField(
                        title = "SNS 주소",
                        textState = snsAddressTextState
                    )

                    BPMSpacer(height = 24.dp)

                    RegisterTextField(
                        title = "영업시간",
                        textState = businessHoursTextState
                    )

                    BPMSpacer(height = 24.dp)

                    RegisterTextField(
                        title = "가격정보",
                        textState = priceInfoTextState
                    )

                    BPMSpacer(height = 35.dp)
                }

                if (!detailInfoExpandState.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableWithoutRipple { }
                    )
                }
            }
        }

        BPMSpacer(height = 35.dp)

        RoundedCornerButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            text = "저장하기",
            textColor = Color.Black,
            buttonColor = MainGreenColor,
            onClick = {
                onClickSave()
            }
        )

        BPMSpacer(height = 12.dp)
    }
}

@Composable
private fun RegisterTextField(
    title: String,
    textState: MutableState<String>,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = SemiBold,
            fontSize = 14.sp,
            letterSpacing = 0.sp,
            color = GrayColor3
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 36.dp)
        ) {
            BPMSpacer(height = 14.dp)

            Box {
                BasicTextField(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth(),
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    ),
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BottomCenter),
                    color = GrayColor6,
                    thickness = 1.dp
                )
            }
        }
    }
}