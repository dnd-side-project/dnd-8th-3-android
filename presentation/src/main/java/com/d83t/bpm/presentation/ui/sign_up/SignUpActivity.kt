package com.d83t.bpm.presentation.ui.sign_up

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.TextFieldColorProvider
import com.d83t.bpm.presentation.compose.theme.*

class SignUpActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val nicknameTextState = mutableStateOf("")
    private val bioTextState = mutableStateOf("")

    override fun initUi() {
        setContent {
            BPMTheme {
                SignUpActivityContent(
                    nicknameTextState = nicknameTextState,
                    bioTextState = bioTextState,
                    onClickSave = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private inline fun SignUpActivityContent(
    nicknameTextState: MutableState<String>,
    bioTextState: MutableState<String>,
    crossinline onClickSave: () -> Unit
) {
    val omissionState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(header = "프로필 생성하기")

            BPMSpacer(height = 44.dp)

            Column(
                modifier = Modifier.align(CenterHorizontally),
                horizontalAlignment = CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(130.dp)
                        .align(CenterHorizontally),
                    painter = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "profileImage"
                )

                BPMSpacer(height = 16.dp)

                Text(
                    text = "프로필 사진 등록",
                    fontWeight = Medium,
                    fontSize = 15.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.Underline,
                    color = GrayColor12
                )
            }

            BPMSpacer(height = 50.dp)

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ProfileTextField(
                    isEssential = true,
                    title = "닉네임",
                    hint = "닉네임",
                    textState = nicknameTextState,
                    omissionState = omissionState
                )

                BPMSpacer(height = 24.dp)

                ProfileTextField(
                    isEssential = false,
                    title = "한줄 소개",
                    hint = "안녕하세요!!",
                    textState = bioTextState
                )
            }
        }

        RoundedCornerButton(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 13.dp
                )
                .fillMaxWidth()
                .height(48.dp)
                .align(BottomCenter),
            text = "저장하기",
            textColor = GrayColor7,
            buttonColor = GrayColor11,
            onClick = {
                if (nicknameTextState.value.isEmpty()) {
                    omissionState.value = true
                }
                onClickSave()
            }
        )
    }
}

@Composable
private fun ProfileTextField(
    isEssential: Boolean,
    title: String,
    hint: String,
    textState: MutableState<String>,
    omissionState: MutableState<Boolean>? = null
) {
    Column {
        Row(modifier = Modifier.padding(start = 2.dp)) {
            Text(
                text = title,
                fontWeight = Medium,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )

            if (isEssential) {
                Text(
                    text = "*",
                    fontWeight = Medium,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    color = Color.Red
                )
            }
        }

        BPMSpacer(height = 10.dp)

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(42.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = if (textState.value.isNotEmpty()) Color.Black else if (omissionState?.value == true) Color.Red else GrayColor7
                )
        ) {
            TextFieldColorProvider {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                        .align(Center),
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    textStyle = TextStyle(
                        fontWeight = Medium,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp
                    ),
                    cursorBrush = SolidColor(Color.Black),
                )

                if (textState.value.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .fillMaxWidth()
                            .align(Center),
                        text = hint,
                        fontWeight = Medium,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor7,
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    )
                }
            }
        }

        if (omissionState?.value == true) {
            BPMSpacer(height = 6.dp)

            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = "필수 입력 정보 입니다.",
                fontWeight = Medium,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = Color.Red
            )
        }
    }
}