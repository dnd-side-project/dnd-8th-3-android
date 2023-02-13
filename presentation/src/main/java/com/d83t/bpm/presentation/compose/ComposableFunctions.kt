package com.d83t.bpm.presentation.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.compose.theme.BPMShapes
import com.d83t.bpm.presentation.compose.theme.BPMTypography
import com.d83t.bpm.presentation.compose.theme.GrayColor8
import com.d83t.bpm.presentation.util.clickableWithoutRipple

@Composable
fun ScreenHeader(header: String) {
    val context = LocalContext.current as ComponentActivity

    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(26.dp)
                    .align(CenterStart)
                    .clickableWithoutRipple { context.finish() },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.align(Center),
                text = header,
                style = BPMTypography.h1
            )
        }
        Divider(
            color = GrayColor8,
            thickness = 1.dp
        )
    }
}

@Composable
fun BPMSpacer(
    height: Dp = 0.dp,
    width: Dp = 0.dp
) {
    Spacer(
        modifier = Modifier
            .height(height)
            .width(width)
    )
}

@Composable
inline fun RoundedCornerButton(
    modifier: Modifier,
    text: String,
    textColor: Color,
    buttonColor: Color,
    crossinline onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(BPMShapes.medium)
            .background(color = buttonColor)
            .clickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier.align(Center),
            text = text,
            color = textColor,
            style = BPMTypography.button
        )
    }
}