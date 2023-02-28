package com.d83t.bpm.presentation.ui.register_location

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
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
import com.d83t.bpm.presentation.compose.TextFieldColorProvider
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener

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
                    onClickChangeLocation = { _, _ ->

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
    onClickChangeLocation: (Double, Double) -> Unit,
    onClickSetLocation: (Double, Double) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val changeableState = remember { mutableStateOf(false) }
    val locationState = remember { mutableStateOf<MapPoint?>(null) }

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
                    TextFieldColorProvider {
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
                            cursorBrush = SolidColor(GrayColor3),
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickableWithoutRipple {
                                focusManager.clearFocus()
                                onClickSearch()
                            }
                            .align(CenterEnd),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "searchLocationIcon"
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BottomCenter),
                    color = GrayColor7,
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

                            setMapViewEventListener(object : MapViewEventListener {
                                override fun onMapViewInitialized(p0: MapView?) {
                                    locationState.value = p0?.mapCenterPoint
                                }

                                override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
                                override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
                                override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                                    if (p1 != null) {
                                        with(p1.mapPointGeoCoord) {
                                            if (String.format("%.6f", latitude) != (35.858902).toString() ||
                                                String.format("%.6f", longitude) != (128.498795).toString()
                                            ) {
                                                changeableState.value = true
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    }
                )

                Image(
                    modifier = Modifier
                        .size(54.dp)
                        .align(Center),
                    painter = painterResource(id = R.drawable.ic_marker),
                    contentDescription = "mapMarkerIcon"
                )

                Box(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(shape = RoundedCornerShape(50.dp))
                        .border(
                            width = 1.dp,
                            color = if (changeableState.value) GrayColor3 else GrayColor8,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .height(36.dp)
                        .background(color = Color.White)
                        .align(BottomCenter)
                        .clickable {
                            if (locationState.value != null) {
                                onClickChangeLocation(
                                    locationState.value!!.mapPointGeoCoord.latitude,
                                    locationState.value!!.mapPointGeoCoord.longitude
                                )
                            }
                        },
                ) {
                    Row(
                        modifier = Modifier.align(Center),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BPMSpacer(width = 14.dp)

                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = "이 위치로 변경하기",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            letterSpacing = 0.sp,
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                            color = if (changeableState.value) Color.Black else GrayColor6
                        )

                        BPMSpacer(width = 14.dp)
                    }
                }
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
                text = "위치 등록하기",
                textColor = Color.Black,
                buttonColor = MainGreenColor,
                onClick = { onClickSetLocation(0.0, 0.0) }
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
        onClickChangeLocation = { _, _ ->

        },
        onClickSetLocation = { _, _ ->

        }
    )
}