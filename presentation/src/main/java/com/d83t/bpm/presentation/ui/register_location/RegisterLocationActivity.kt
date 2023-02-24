package com.d83t.bpm.presentation.ui.register_location

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.BPMTheme
import com.d83t.bpm.presentation.compose.theme.GrayColor14
import com.d83t.bpm.presentation.compose.theme.MainGreenColor
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class RegisterLocationActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val searchTextState = mutableStateOf("")

    override fun initUi() {
        setContent {
            BPMTheme {
                RegisterLocationActivityContent(
                    searchTextState = searchTextState,
                    onClickSearch = {

                    },
                    onClickSetLocation = { _, _ ->

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private fun RegisterLocationActivityContent(
    searchTextState: MutableState<String>,
    onClickSearch: () -> Unit,
    onClickSetLocation: (Double, Double) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(bottom = 74.dp)
                .fillMaxSize()
        ) {
            ScreenHeader(header = "위치 등록하기")

            BPMSpacer(height = 12.dp)

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .height(34.dp)
            ) {
                Box {
                    BasicTextField(
                        modifier = Modifier
                            .padding(end = 42.dp)
                            .fillMaxWidth()
                            .align(Center),
                        value = searchTextState.value,
                        onValueChange = { searchTextState.value = it },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            letterSpacing = 0.sp,
                            color = Color.Black
                        ),
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )

                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickableWithoutRipple { onClickSearch() }
                            .align(CenterEnd),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "searchLocationIcon"
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BottomCenter),
                    color = GrayColor14,
                    thickness = 1.dp
                )
            }

            BPMSpacer(height = 12.dp)

            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        MapView(context).apply {
                            setMapCenterPoint(
                                MapPoint.mapPointWithGeoCoord(
                                    35.8589,
                                    128.4988
                                ),
                                false
                            )
                        }
                    }
                )

                Image(
                    modifier = Modifier
                        .size(46.dp)
                        .align(Center),
                    painter = painterResource(id = R.drawable.ic_marker),
                    contentDescription = "mapMarkerIcon"
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .align(BottomCenter)
        ) {
            RoundedCornerButton(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 13.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                text = "이 위치로 설정하기",
                textColor = Color.Black,
                buttonColor = MainGreenColor,
                onClick = {
                    onClickSetLocation(0.0, 0.0)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RegisterLocationActivityContent(
        searchTextState = remember { mutableStateOf("") },
        onClickSearch = {

        },
        onClickSetLocation = { _, _ ->

        }
    )
}