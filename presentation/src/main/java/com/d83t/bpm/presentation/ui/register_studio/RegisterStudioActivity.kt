package com.d83t.bpm.presentation.ui.register_studio

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.BPMTheme
import com.d83t.bpm.presentation.compose.theme.GrayColor3
import com.d83t.bpm.presentation.compose.theme.GrayColor6
import com.d83t.bpm.presentation.compose.theme.pretendard

class RegisterStudioActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val studioNameTextState = mutableStateOf("")
    private val studioLocationTextState = mutableStateOf("테스트 주소로 테스트길 52, 12-3")

    override fun initUi() {
        setContent {
            BPMTheme {
                RegisterStudioActivityContent(
                    studioNameTextState = studioNameTextState,
                    studioLocationTextState = studioLocationTextState
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private fun RegisterStudioActivityContent(
    studioNameTextState: MutableState<String>,
    studioLocationTextState: MutableState<String>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
    ) {
        ScreenHeader(header = "새 업체 등록하기")

        BPMSpacer(height = 32.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "업체 이름",
                fontFamily = pretendard,
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
                BasicTextField(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                        .align(BottomCenter),
                    value = studioNameTextState.value,
                    onValueChange = { studioNameTextState.value = it },
                    textStyle = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    )
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

        BPMSpacer(height = 25.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "위치",
                fontFamily = pretendard,
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
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    color = Color.Black
                )

                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(TopEnd),
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

    }
}