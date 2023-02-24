package com.d83t.bpm.presentation.ui.writing_review

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.base.BaseViewModel
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.KeywordChip
import com.d83t.bpm.presentation.compose.RoundedCornerButton
import com.d83t.bpm.presentation.compose.ScreenHeader
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.register_studio.dummyKeywordChipList
import com.d83t.bpm.presentation.util.addFocusCleaner
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import com.d83t.bpm.presentation.util.convertUriToBitmap
import com.google.accompanist.flowlayout.FlowRow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WritingReviewActivity : BaseComponentActivity() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private lateinit var addImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var replaceImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private var imageStateList = SnapshotStateList<ImageBitmap>()
    private var replaceImageIndex = -1
    private val contentTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        addImageLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            uris?.let { _ ->
                if (uris.size + imageStateList.size <= 5) {
                    try {
                        imageStateList.addAll(uris.map { uri ->
                            convertUriToBitmap(
                                contentResolver = contentResolver,
                                uri = uri
                            ).asImageBitmap()
                        })
                        refreshImageList()
                    } catch (e: Exception) {
                        // TODO : Handle Error
                    }
                } else {
                    // TODO : Show Error Dialog
                }
            } ?: run {
                // TODO : Handle Error
            }
        }

        replaceImageLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
            uri?.let { _ ->
                try {
                    imageStateList[replaceImageIndex] = (convertUriToBitmap(
                        contentResolver = contentResolver,
                        uri = uri
                    ).asImageBitmap())
                    refreshImageList()
                } catch (e: Exception) {
                    // TODO : Handle Error
                }
            } ?: run {
                // TODO : Handle Error
            }
        }
    }

    override fun initUi() {
        setContent {
            BPMTheme {
                WritingReviewActivityContent(
                    imageStateList = imageStateList,
                    contentTextState = contentTextState,
                    addImage = { addImageLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
                    replaceImage = { index ->
                        replaceImageIndex = index
                        replaceImageLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    },
                    removeImage = { index ->
                        imageStateList.removeAt(index)
                        refreshImageList()
                    },
                    onClickSendReview = {

                    }
                )
            }
        }
    }

    override fun setupCollect() = Unit

    private fun refreshImageList() {
        val temp = SnapshotStateList<ImageBitmap>().apply { addAll(imageStateList) }
        lifecycleScope.launch {
            imageStateList.clear()
            delay(50)
        }.invokeOnCompletion { imageStateList.addAll(temp) }
    }
}

@Composable
private fun WritingReviewActivityContent(
    imageStateList: SnapshotStateList<ImageBitmap>,
    contentTextState: MutableState<String>,
    addImage: () -> Unit,
    replaceImage: (Int) -> Unit,
    removeImage: (Int) -> Unit,
    onClickSendReview: () -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState.maxValue) {
        if (contentTextState.value.isNotEmpty()) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .windowInsetsPadding(insets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Vertical))
            .imePadding()
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
            .addFocusCleaner(focusManager = LocalFocusManager.current)
    ) {
        ScreenHeader(header = "리뷰 작성하기")

        BPMSpacer(height = 20.dp)

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(104.dp)
                .background(color = GrayColor11)
                .border(
                    width = 1.dp,
                    color = GrayColor9,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .height(74.dp)
            ) {
                Image(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .size(74.dp),
                    painter = painterResource(id = R.drawable.dummy_studio),
                    contentDescription = "studioProfileImage",
                    contentScale = ContentScale.FillBounds
                )

                BPMSpacer(width = 10.dp)

                Column(
                    modifier = Modifier.height(74.dp),
                    verticalArrangement = SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "스튜디오 이름",
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )

                        BPMSpacer(height = 6.dp)

                        Text(
                            text = "스튜디오에 대한 간단한 한 줄 설명을 붙여주세요.",
                            fontWeight = Normal,
                            fontSize = 11.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor3
                        )
                    }

                    Row(verticalAlignment = CenterVertically) {
                        repeat(5) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(id = R.drawable.ic_star_small),
                                contentDescription = "starIcon",
                                tint = GrayColor6
                            )

                            BPMSpacer(width = 2.dp)
                        }

                        BPMSpacer(width = 6.dp)

                        Text(
                            text = "4.0",
                            fontWeight = Normal,
                            fontSize = 11.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor3
                        )
                    }
                }
            }
        }

        BPMSpacer(height = 35.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "이 정도 만족했어요",
            fontWeight = SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )

        BPMSpacer(height = 8.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "만족한 정도를 별점으로 보여주세요",
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.2.sp,
            color = GrayColor4
        )

        BPMSpacer(height = 14.dp)

        Divider(color = GrayColor13)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Row(modifier = Modifier.align(Center)) {
                repeat(5) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star_large),
                        contentDescription = "starIcon",
                        tint = GrayColor8
                    )

                    BPMSpacer(width = 8.dp)
                }
            }
        }

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        BPMSpacer(height = 35.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "이런 점을 추천해요",
            fontWeight = SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )

        BPMSpacer(height = 8.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "최대 5개까지 선택가능해요",
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.2.sp,
            color = GrayColor4
        )

        BPMSpacer(height = 14.dp)

        Divider(color = GrayColor13)

        BPMSpacer(height = 20.dp)

        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 10.dp
        ) {
            dummyKeywordChipList.forEach { dummyKeyword ->
                KeywordChip(text = dummyKeyword)
            }
        }

        BPMSpacer(height = 20.dp)

        Divider(
            thickness = 8.dp,
            color = GrayColor11
        )

        BPMSpacer(height = 35.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "이런 경험이었어요",
            fontWeight = SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )

        BPMSpacer(height = 8.dp)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "사진과 간단한 후기를 적어주세요",
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.2.sp,
            color = GrayColor4
        )

        BPMSpacer(height = 14.dp)

        Divider(color = GrayColor13)

        BPMSpacer(height = 15.dp)

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            if (imageStateList.size < 5) {
                item {
                    ImagePlaceHolder(
                        image = null,
                        onClick = { addImage() }
                    )
                }
            }

            for (i in imageStateList.indices.reversed()) {
                item {
                    ImagePlaceHolder(
                        image = imageStateList[i],
                        onClick = { replaceImage(i) },
                        onClickRemove = { removeImage(i) }
                    )
                }
            }
        }

        BPMSpacer(height = 12.dp)

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .heightIn(min = 262.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(6.dp),
                    color = GrayColor6
                )
                .background(color = Color.White)
        ) {
            CompositionLocalProvider(LocalTextSelectionColors.provides(textSelectionColor())) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 262.dp),
                    value = contentTextState.value,
                    onValueChange = { contentTextState.value = it },
                    textStyle = TextStyle(
                        fontWeight = Normal,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    ),
                    colors = textFieldColors()
                )
            }
        }

        BPMSpacer(height = 50.dp)

        RoundedCornerButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            text = "저장하기",
            textColor = Color.Black,
            buttonColor = MainGreenColor,
            onClick = {
                onClickSendReview()
            }
        )

        BPMSpacer(height = 12.dp)
    }
}

@Composable
private fun ImagePlaceHolder(
    image: ImageBitmap?,
    onClick: () -> Unit,
    onClickRemove: (() -> Unit)? = null
) {
    val imageState = remember { mutableStateOf(image) }
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.size(105.dp)) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = GrayColor10)
            .align(BottomStart)
            .clickable {
                onClick()
                focusManager.clearFocus()
            }
        ) {
            if (imageState.value != null) {
                Image(
                    bitmap = imageState.value!!,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )
            } else {
                // TODO : Show Icon
            }
        }

        if (imageState.value != null) {
            Box(modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape
                )
                .clip(shape = CircleShape)
                .size(20.dp)
                .background(color = Color.White)
                .align(TopEnd)
                .clickableWithoutRipple { onClickRemove?.invoke() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Center),
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "removeImageIcon"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
}