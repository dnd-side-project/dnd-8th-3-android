package com.d83t.bpm.presentation.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextOverflow
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
fun BPMTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String>,
    label: String,
    limit: Int? = null,
    minHeight: Dp = 42.dp,
    iconSize: Dp = 0.dp,
    singleLine: Boolean,
    hint: String? = null,
    onClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    icon: @Composable (BoxScope.() -> Unit)? = null
) {
    Column(modifier = modifier.background(color = Color.White)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .fillMaxWidth(),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = label,
                fontWeight = Medium,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = GrayColor4
            )

            if (limit != null) {
                Text(
                    text = "${textState.value.length}/$limit",
                    fontWeight = Medium,
                    fontSize = 10.sp,
                    letterSpacing = 0.sp,
                    color = GrayColor4
                )
            }
        }

        BPMSpacer(height = 10.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = if (textState.value.isNotEmpty()) GrayColor5 else GrayColor6
                )
                .clickableWithoutRipple { onClick?.invoke() }
        ) {
            if (hint != null && textState.value.isEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = 14.dp,
                            vertical = 12.dp
                        )
                        .heightIn(min = minHeight - 24.dp)
                        .align(TopStart),
                    text = hint,
                    fontWeight = Medium,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    color = GrayColor6
                )
            }

            if (icon != null) {
                icon()
            }

            if (onClick == null) {
                CompositionLocalProvider(LocalTextSelectionColors.provides(textSelectionColor())) {
                    BasicTextField(
                        modifier = Modifier
                            .padding(
                                horizontal = 14.dp,
                                vertical = 12.dp
                            )
                            .fillMaxWidth()
                            .heightIn(min = minHeight - 24.dp)
                            .align(TopStart),
                        value = textState.value,
                        onValueChange = { textState.value = it },
                        singleLine = singleLine,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        cursorBrush = SolidColor(Color.Black),
                        textStyle = TextStyle(
                            fontWeight = Medium,
                            fontSize = 13.sp,
                            letterSpacing = 0.sp,
                            color = Color.Black
                        )
                    )
                }
            } else {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 14.dp,
                            end = if (icon == null) 14.dp else 14.dp + iconSize,
                        )
                        .padding(vertical = 8.dp)
                        .align(TopStart),
                    text = textState.value,
                    fontWeight = Medium,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun ReviewComposable(
    isLiked: Boolean
) {
    val likeState = remember { mutableStateOf(isLiked) }

    Column(modifier = Modifier.fillMaxWidth()) {
        BPMSpacer(height = 16.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(verticalAlignment = CenterVertically) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "profileImage"
                )

                BPMSpacer(width = 8.dp)

                Text(
                    text = "닉네임",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            }

            Text(
                text = "2023.02.15",
                fontWeight = Medium,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        BPMSpacer(height = 12.dp)

        Row {
            repeat(5) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_small),
                    contentDescription = "starIcon",
                    tint = GrayColor6
                )

                BPMSpacer(width = 2.dp)
            }
        }

        BPMSpacer(height = 14.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            KeywordChip(text = "🤔 친절해요")
            KeywordChip(text = "😍 소통이 빨라요")
            KeywordChip(text = "+3")
        }

        BPMSpacer(height = 14.dp)

        Row {
            repeat(5) { index ->
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    painter = painterResource(id = R.drawable.dummy_studio),
                    contentDescription = "reviewImage",
                    contentScale = ContentScale.Crop
                )

                if (index != 4) {
                    BPMSpacer(width = 4.dp)
                }
            }
        }

        BPMSpacer(height = 10.dp)

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "One, two, three, four Baby, got me looking so crazy 빠져버리는 daydream got me feeling you 너도 말해줄래 누가 내게 뭐라든 남들과는 달라 넌 Maybe you could be the one 날 믿어봐 한 번 I'm not looking for just fun Maybe I could be the one Oh baby 예민하대 나 lately 너 없이는 나 매일매일이 yeah 재미없어 어쩌지 I just want you Call my phone right now I just wanna hear you're mine",
            fontWeight = Normal,
            fontSize = 13.sp,
            letterSpacing = 0.sp,
            maxLines = 4,
            lineHeight = 19.sp,
            overflow = TextOverflow.Ellipsis
        )

        BPMSpacer(height = 25.dp)

        LikeButton(
            likeState = likeState,
            onClick = { }
        )
    }

    BPMSpacer(height = 20.dp)

    Divider(color = GrayColor13)
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
            verticalAlignment = CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "likeIcon",
                tint = if (likeState.value) MainGreenColor else Color.Black
            )

            BPMSpacer(width = 4.dp)

            Text(
                text = "좋아요",
                fontWeight = Medium,
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
        fontWeight = Medium,
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