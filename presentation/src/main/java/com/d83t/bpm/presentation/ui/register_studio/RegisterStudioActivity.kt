package com.d83t.bpm.presentation.ui.register_studio

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.*
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.addFocusCleaner
import com.google.accompanist.flowlayout.FlowRow
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

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(55.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "업체 정보",
                textAlign = TextAlign.Center,
                fontWeight = SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )

            Text(
                text = "*",
                fontWeight = SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.sp,
                color = Color.Red
            )
        }

        Divider(color = GrayColor13)

        BPMSpacer(height = 25.dp)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            BPMTextField(
                textState = studioNameTextState,
                label = "업체 이름",
                singleLine = true,
                hint = "업체 이름을 입력해주세요"
            )

            BPMSpacer(height = 25.dp)

            BPMTextField(
                textState = studioLocationTextState,
                label = "위치",
                hint = "업체 위치를 등록해주세요",
                iconSize = 30.dp,
                singleLine = true,
                icon = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                            .align(CenterEnd),
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "locationIcon",
                        tint = GrayColor6
                    )
                },
                onClick = { onClickLocation() }
            )
        }

        BPMSpacer(height = 30.dp)

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        BPMSpacer(height = 20.dp)

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
                KeywordChip(
                    text = dummyKeyword,
                    onClick = {}
                )
            }
        }

        BPMSpacer(height = 30.dp)

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(
                modifier = Modifier.align(CenterStart),
                text = "업체 추가 정보",
                fontWeight = SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )
        }

        Divider(color = GrayColor8)

        BPMSpacer(height = 26.dp)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            LaunchedEffect(key1 = scrollState.maxValue) {
                scope.launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }

            BPMTextField(
                textState = phoneNumberTextState,
                label = "전화번호",
                hint = "000-0000-0000",
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            BPMSpacer(height = 22.dp)

            BPMTextField(
                textState = snsAddressTextState,
                label = "SNS 주소",
                hint = "인스타그램 @BodyProfileManager",
                singleLine = false
            )

            BPMSpacer(height = 22.dp)

            BPMTextField(
                textState = businessHoursTextState,
                label = "영업시간",
                hint = "12:00~19:00",
                singleLine = false
            )

            BPMSpacer(height = 22.dp)

            BPMTextField(
                textState = priceInfoTextState,
                label = "가격정보",
                hint = "프로필 0000원",
                singleLine = false
            )
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