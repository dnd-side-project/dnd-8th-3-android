package com.d83t.bpm.presentation.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.clickableWithoutRipple

@Composable
fun ScreenHeader(header: String) {
    val context = LocalContext.current as ComponentActivity

    Column(modifier = Modifier.fillMaxWidth()) {
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
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 0.sp
            )
        }

        Divider(
            thickness = 1.dp,
            color = GrayColor8
        )
    }
}

@Composable
fun TextFieldColorProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalTextSelectionColors.provides(textSelectionColor())) {
        content()
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
            .clip(RoundedCornerShape(8.dp))
            .background(color = buttonColor)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Center),
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )
    }
}

@Composable
inline fun OutLinedRoundedCornerButton(
    modifier: Modifier,
    text: String,
    textColor: Color,
    buttonColor: Color,
    outLineColor: Color,
    crossinline onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = outLineColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = buttonColor)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Center),
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )
    }
}

@Composable
inline fun LikeButton(
    likeState: MutableState<Boolean>,
    crossinline onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .height(28.dp)
            .border(
                width = 1.dp,
                color = if (likeState.value) Color.Black else GrayColor9,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = if (likeState.value) Color.Black else Color.White)
            .clickableWithoutRipple {
                likeState.value = !likeState.value
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "likeIcon",
                tint = if (likeState.value) MainGreenColor else Color.Black
            )

            BPMSpacer(width = 4.dp)

            Text(
                text = "좋아요",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = if (likeState.value) MainGreenColor else Color.Black
            )

            BPMSpacer(width = 4.dp)

            Text(
                text = "12",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = if (likeState.value) MainGreenColor else Color.Black
            )
        }
    }
}

@Composable
fun KeywordChip(
    text: String,
    isChosen: Boolean = false
) {
    val selectState = remember { mutableStateOf(isChosen) }

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(60.dp))
            .background(color = if (selectState.value) MainGreenColor else GrayColor9)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .clickableWithoutRipple { selectState.value = !selectState.value },
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        color = if (selectState.value) Color.Black else GrayColor4
    )
}

@Composable
fun ReviewKeywordChip(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(color = GrayColor9),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "🤔",
                fontSize = 12.sp,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )

            Text(
                text = text,
                fontWeight = Normal,
                fontSize = 12.sp,
                color = GrayColor3,
                letterSpacing = 0.sp,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
        }
    }
}