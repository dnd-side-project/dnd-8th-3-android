package com.d83t.bpm.presentation.compose

import androidx.activity.ComponentActivity
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.d83t.bpm.domain.model.Review
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.studio_detail.review_detail.ReviewDetailActivity
import com.d83t.bpm.presentation.ui.studio_detail.writing_review.WritingReviewActivity
import com.d83t.bpm.presentation.util.clickableWithoutRipple

@Composable
fun ScreenHeader(
    header: String,
    actionBlock: @Composable (() -> Unit)? = null
) {
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

            if (actionBlock != null) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(CenterEnd)
                ) {
                    actionBlock()
                }
            }
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewComposable(
    modifier: Modifier = Modifier,
    review: Review
) {
    val context = LocalContext.current as BaseComponentActivity

    with(review) {
        val likeState = remember { mutableStateOf(liked ?: false) }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickableWithoutRipple {
                    context.startActivity(
                        ReviewDetailActivity
                            .newIntent(context)
                            .putExtra("reviewId", id)
                    )
                }
        ) {
            BPMSpacer(height = 16.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row(verticalAlignment = CenterVertically) {
                    GlideImage(
                        modifier = Modifier.size(24.dp),
                        model = author?.profilePath ?: "",
                        contentDescription = "profileImage"
                    )

                    BPMSpacer(width = 8.dp)

                    Text(
                        text = author?.nickname ?: "",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp
                    )
                }

                Text(
                    text = createdAt?.dropLast(10)?.replace("-", ".") ?: "",
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
                recommends?.forEach { keyword ->
                    KeywordChip(text = keyword)
                }
            }

            BPMSpacer(height = 14.dp)

            Row {
                filesPath?.forEachIndexed { index, image ->
                    GlideImage(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        model = image,
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
                text = content ?: "",
                fontWeight = Normal,
                fontSize = 13.sp,
                letterSpacing = 0.sp,
                maxLines = 4,
                lineHeight = 19.sp,
                overflow = TextOverflow.Ellipsis
            )

            BPMSpacer(height = 25.dp)

            LikeButton(
                liked = review.liked ?: false,
                likeState = likeState,
                likeCount = likeCount ?: 0,
                onClick = { }
            )
        }

        BPMSpacer(height = 20.dp)

        Divider(color = GrayColor13)
    }

}

@Composable
inline fun LikeButton(
    liked: Boolean,
    likeState: MutableState<Boolean>,
    likeCount: Int,
    crossinline onClick: () -> Unit
) {
    val defaultLiked = liked

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
                text = if (defaultLiked &&
                    likeState.value
                ) "$likeCount"
                else if (defaultLiked &&
                    !likeState.value
                ) "${likeCount - 1}"
                else if (!defaultLiked &&
                    !likeState.value
                ) "$likeCount"
                else "${likeCount + 1}",
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

@Composable
fun ReviewListHeader(
    modifier: Modifier = Modifier,
    reviewCount: Int,
    showImageReviewOnlyState: MutableState<Boolean>,
    showReviewOrderByLikeState: MutableState<Boolean>,
) {
    val context = LocalContext.current as BaseComponentActivity

    Column {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(55.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "업체 리뷰 $reviewCount",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )

            Text(
                modifier = Modifier.clickableWithoutRipple { context.startActivity(WritingReviewActivity.newIntent(context = context)) },
                text = "리뷰 작성하기",
                fontWeight = Medium,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                color = GrayColor4
            )
        }

        Divider(color = GrayColor13)

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(55.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(modifier = Modifier.clickableWithoutRipple { showImageReviewOnlyState.value = !showImageReviewOnlyState.value }) {
                Icon(
                    modifier = Modifier.align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_check_field),
                    contentDescription = "checkFieldIcon",
                    tint = if (showImageReviewOnlyState.value) Color.Black else GrayColor7
                )

                BPMSpacer(width = 8.dp)

                Text(
                    text = "사진 리뷰만 보기",
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            }

            Row {
                Text(
                    modifier = Modifier.clickableWithoutRipple { showReviewOrderByLikeState.value = true },
                    text = "좋아요순",
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    color = if (showReviewOrderByLikeState.value) Color.Black else GrayColor6
                )

                BPMSpacer(width = 20.dp)

                Divider(
                    modifier = Modifier
                        .height(12.dp)
                        .width(1.dp)
                        .align(CenterVertically),
                    color = GrayColor3
                )

                BPMSpacer(width = 20.dp)

                Text(
                    modifier = Modifier.clickableWithoutRipple { showReviewOrderByLikeState.value = false },
                    text = "최신순",
                    fontWeight = Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    color = if (showReviewOrderByLikeState.value) GrayColor6 else Color.Black
                )
            }
        }

        Divider(color = GrayColor13)
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x50000000))
            .clickableWithoutRipple { }
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Center),
            color = MainGreenColor
        )
    }
}