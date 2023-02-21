package com.d83t.bpm.presentation.ui.making_reservation

import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
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
import com.d83t.bpm.presentation.compose.theme.BPMTheme
import com.d83t.bpm.presentation.compose.theme.GrayColor5
import com.d83t.bpm.presentation.compose.theme.GrayColor6
import com.d83t.bpm.presentation.compose.theme.pretendard
import com.d83t.bpm.presentation.util.clickableWithoutRipple

class MakingReservationActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun initUi() {
        setContent {
            BPMTheme {
                MakingReservationActivityContent(
                    onClickSearchStudio = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit
}

@Composable
private inline fun MakingReservationActivityContent(
    crossinline onClickSearchStudio: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
    ) {
        ScreenHeader(header = "일정 확정하기")

        MakingReservationItem(
            title = "스튜디오",
            expandedHeight = 124.dp,
            content = {
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
        )
    }
}

@Composable
private fun MakingReservationItem(
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
            Text(
                modifier = Modifier.height(24.dp),
                text = title,
                textAlign = TextAlign.Center,
                fontFamily = pretendard,
                fontWeight = Medium,
                fontSize = 17.sp,
                letterSpacing = 0.sp
            )

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