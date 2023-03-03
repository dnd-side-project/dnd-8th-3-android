package com.d83t.bpm.presentation.ui.studio_detail.writing_review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.*
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.register_studio.dummyKeywordChipList
import com.d83t.bpm.presentation.ui.studio_detail.review_detail.ReviewDetailActivity
import com.d83t.bpm.presentation.util.*
import com.google.accompanist.flowlayout.FlowRow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WritingReviewActivity : BaseComponentActivity() {
    override val viewModel: WritingReviewViewModel by viewModels()

    private lateinit var addImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var replaceImageLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    private var studioId: Int = 0

    private var imageStateList = SnapshotStateList<ImageBitmap>()
    private var replaceImageIndex = -1

    private val studioState = mutableStateOf<Studio?>(null)
    private val recommendsStateList = SnapshotStateList<String>()
    private val ratingState = mutableStateOf(0.0)
    private val contentTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studioId = intent.getIntExtra("studioId", 0)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        initComposeUi {
            if (studioState.value != null) {
                WritingReviewActivityContent(
                    studio = studioState.value!!,
                    imageStateList = imageStateList,
                    ratingState = ratingState,
                    recommendsStateList = recommendsStateList,
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
                    onClickWriteReview = { viewModel.onClickWriteReview() }
                )
            }
        }

        addImageLauncher =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
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

    override fun initUi() = Unit

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is WritingReviewViewEvent.Write -> viewModel.writeReview(
                        studioId = studioId,
                        images = imageStateList,
                        rating = ratingState.value,
                        recommends = recommendsStateList,
                        content = contentTextState.value
                    )
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is WritingReviewState.Init -> viewModel.getStudio(studioId = studioId)
                    is WritingReviewState.Loading -> showLoadingScreen()
                    is WritingReviewState.StudioSuccess -> {
                        hideLoadingScreen()
                        studioState.value = state.studio
                    }
                    is WritingReviewState.ReviewSuccess -> {
                        hideLoadingScreen()
                        startActivity(ReviewDetailActivity.newIntent(this@WritingReviewActivity).putExtra("reviewId", state.review.id))
                        finish()
                    }
                    is WritingReviewState.Error -> Unit
                }
            }
        }
    }

    private fun refreshImageList() {
        val temp = SnapshotStateList<ImageBitmap>().apply { addAll(imageStateList) }
        lifecycleScope.launch {
            imageStateList.clear()
            delay(50)
        }.invokeOnCompletion { imageStateList.addAll(temp) }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, WritingReviewActivity::class.java)
        }

    }
}

@Composable
private fun WritingReviewActivityContent(
    studio: Studio,
    imageStateList: SnapshotStateList<ImageBitmap>,
    ratingState: MutableState<Double>,
    recommendsStateList: SnapshotStateList<String>,
    contentTextState: MutableState<String>,
    addImage: () -> Unit,
    replaceImage: (Int) -> Unit,
    removeImage: (Int) -> Unit,
    onClickWriteReview: () -> Unit
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
                            text = studio.name ?: "",
                            fontWeight = Medium,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )

                        BPMSpacer(height = 6.dp)

                        Text(
                            text = studio.content ?: "",
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
                                painter = painterResource(id = R.drawable.ic_star_small_empty),
                                contentDescription = "starIcon",
                                tint = GrayColor6
                            )

                            BPMSpacer(width = 2.dp)
                        }

                        BPMSpacer(width = 6.dp)

                        Text(
                            text = "${studio.rating?.clip() ?: 0.0}",
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
            val ratingDraggableAreaWidthState = remember { mutableStateOf(0) }

            Row(
                modifier = Modifier.align(Center),
                horizontalArrangement = spacedBy(8.dp)
            ) {
                for (i in 1..5) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(
                            id = if (i.toDouble() <= ratingState.value) R.drawable.ic_star_large_filled
                            else if (i.toDouble() > ratingState.value && ratingState.value > i - 1) R.drawable.ic_star_large_half
                            else R.drawable.ic_star_large_empty
                        ),
                        contentDescription = "starIcon"
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Center)
                    .onGloballyPositioned { coordinates ->
                        ratingDraggableAreaWidthState.value = coordinates.size.width
                    }
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            ratingState.value =
                                if (change.position.x >= ratingDraggableAreaWidthState.value) 5.0
                                else (if (change.position.x <= 0) 0f
                                else (change.position.x / ratingDraggableAreaWidthState.value) * 5).toDouble()
                        }
                    }
            ) {
                for (i in 1..10) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(36.dp)
                            .clickableWithoutRipple { ratingState.value = i * 0.5 }
                    )

                    if (i % 2 == 0 && i != 10) {
                        BPMSpacer(width = 8.dp)
                    }
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
                KeywordChip(
                    text = dummyKeyword,
                    onClick = { recommendsStateList.add(dummyKeyword) }
                )
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
            text = "사진과 간단한 후기를 적어주세요 (최대 5장)",
            fontWeight = Medium,
            fontSize = 12.sp,
            letterSpacing = 0.2.sp,
            color = GrayColor4
        )

        BPMSpacer(height = 14.dp)

        Divider(color = GrayColor13)

        BPMSpacer(height = 15.dp)

        LazyRow(
            horizontalArrangement = spacedBy(12.dp),
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

        BPMSpacer(height = 20.dp)

        BPMTextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            textState = contentTextState,
            singleLine = false,
            label = "바디프로필 촬영 경험담을 들려주세요",
            limit = 300,
            minHeight = 262.dp,
            hint = "내용을 입력해주세요."
        )

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
                onClickWriteReview()
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
                Image(
                    modifier = Modifier.align(Center),
                    painter = painterResource(id = R.drawable.ic_add_image),
                    contentDescription = "addImage"
                )
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