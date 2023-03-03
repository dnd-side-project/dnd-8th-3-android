package com.d83t.bpm.presentation.ui.schedule.select_studio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.BPMSpacer
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.util.clickableWithoutRipple
import com.d83t.bpm.presentation.util.clip
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectStudioActivity : BaseComponentActivity() {
    override val viewModel: SelectStudioViewModel by viewModels()

    private val searchTextState = mutableStateOf("")
    private val studioStateList = mutableStateOf<List<Studio>>(listOf())
    private val selectedStudioIdState = mutableStateOf(-1)
    private val selectedStudioNameState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComposeUi {
            SelectStudioActivityContent(
                searchTextState = searchTextState,
                studioList = studioStateList.value,
                selectedStudioId = selectedStudioIdState.value,
                onSearch = { viewModel.onClickSearch() },
                onClickCheckBox = { id, name ->
                    selectedStudioIdState.value = id
                    selectedStudioNameState.value = name
                },
                onClickSelect = {
                    intent.putExtra("studioName", selectedStudioNameState.value)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            )
        }
    }

    override fun initUi() = Unit

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is SelectStudioViewEvent.Search -> {
                        viewModel.searchStudio(searchTextState.value)
                    }
                }
            }
        }

        repeatCallDefaultOnStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is SelectStudioState.Init -> Unit
                    is SelectStudioState.Loading -> showLoadingScreen()
                    is SelectStudioState.Success -> {
                        hideLoadingScreen()
                        studioStateList.value = state.studioList
                    }
                    is SelectStudioState.Error -> hideLoadingScreen()
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SelectStudioActivity::class.java)
        }

    }
}

@Composable
private inline fun SelectStudioActivityContent(
    searchTextState: MutableState<String>,
    selectedStudioId: Int,
    studioList: List<Studio>,
    crossinline onSearch: () -> Unit,
    crossinline onClickCheckBox: (Int, String) -> Unit,
    crossinline onClickSelect: () -> Unit
) {
    val context = LocalContext.current as BaseComponentActivity
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        if (studioList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 56.dp,
                        bottom = 76.dp
                    )
            ) {
                items(studioList) { studio ->
                    StudioWithCheckBox(
                        studio = studio,
                        selectedStudioId = selectedStudioId,
                        onClick = { selectedStudioId, selectedStudioName ->
                            onClickCheckBox(
                                selectedStudioId,
                                selectedStudioName
                            )
                        }
                    )
                }
            }
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(26.dp)
                        .align(CenterVertically)
                        .clickableWithoutRipple { context.finish() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = ""
                )

                BPMSpacer(width = 16.dp)

                Box(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(12.dp),
                            color = GrayColor8
                        )
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    CompositionLocalProvider(LocalTextSelectionColors.provides(textSelectionColor())) {
                        BasicTextField(
                            modifier = Modifier
                                .padding(
                                    start = 14.dp,
                                    end = 50.dp
                                )
                                .fillMaxWidth()
                                .align(CenterStart),
                            value = searchTextState.value,
                            onValueChange = { searchTextState.value = it },
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                                onSearch()
                            }), // TODO OnDoneAction
                            cursorBrush = SolidColor(Color.Black),
                            textStyle = TextStyle(
                                fontWeight = Medium,
                                fontSize = 14.sp,
                                letterSpacing = 0.sp
                            )
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(32.dp)
                            .align(CenterEnd)
                            .clickableWithoutRipple {
                                focusManager.clearFocus()
                                onSearch()
                            },
                        painter = painterResource(
                            id = R.drawable.ic_search
                        ),
                        contentDescription = "searchIcon",
                        tint = GrayColor7
                    )
                }
            }
        }

        if (studioList.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 14.dp
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = if (selectedStudioId != -1) MainGreenColor else GrayColor9)
                    .align(BottomCenter)
                    .clickable { onClickSelect() }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "선택 완료",
                    fontWeight = SemiBold,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    color = if (selectedStudioId != -1) Color.Black else GrayColor7
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun StudioWithCheckBox(
    studio: Studio,
    selectedStudioId: Int,
    onClick: (Int, String) -> Unit
) {
    val selectedState = studio.id == selectedStudioId

    with(studio) {
        Column(modifier = Modifier.fillMaxWidth()) {
            BPMSpacer(height = 18.dp)

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = name ?: "",
                        fontWeight = SemiBold,
                        fontSize = 15.sp,
                        letterSpacing = 0.sp
                    )

                    Icon(
                        modifier = Modifier.clickable {
                            onClick(
                                studio.id ?: 0,
                                studio.name ?: ""
                            )
                        },
                        painter = painterResource(id = R.drawable.ic_check_box),
                        contentDescription = "checkBoxIcon",
                        tint = if (selectedState) Color.Black else GrayColor7
                    )
                }

                BPMSpacer(height = 2.dp)

                Text(
                    text = content ?: "",
                    fontWeight = Medium,
                    fontSize = 12.sp,
                    letterSpacing = 0.sp,
                    color = GrayColor4
                )

                Row(verticalAlignment = CenterVertically) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_star_small_filled),
                        contentDescription = "starIcon",
                        tint = MainGreenColor
                    )

                    BPMSpacer(width = 4.dp)

                    Text(
                        text = rating?.clip()!!,
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )

                    BPMSpacer(width = 12.dp)

                    Text(
                        text = "리뷰 ",
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )

                    Text(
                        text = "${reviewCount ?: 0}",
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor1
                    )

                    BPMSpacer(width = 12.dp)

                    Text(
                        text = "스크랩 ",
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor4
                    )

                    Text(
                        text = "${scrapCount ?: 0}",
                        fontWeight = Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor1
                    )
                }

                BPMSpacer(height = 8.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = GrayColor8,
                                shape = RoundedCornerShape(60.dp)
                            )
                            .height(26.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.Center),
                            text = "친절해요",
                            fontWeight = Normal,
                            fontSize = 10.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = GrayColor8,
                                shape = RoundedCornerShape(60.dp)
                            )
                            .height(26.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.Center),
                            text = "소통이 빨라요",
                            fontWeight = Normal,
                            fontSize = 10.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = GrayColor8,
                                shape = RoundedCornerShape(60.dp)
                            )
                            .height(26.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.Center),
                            text = "깨끗해요",
                            fontWeight = Normal,
                            fontSize = 10.sp,
                            letterSpacing = 0.sp,
                            color = GrayColor1
                        )
                    }
                }

                BPMSpacer(height = 10.dp)
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(filesPath!!) { image ->
                    GlideImage(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .width(120.dp)
                            .height(150.dp),
                        model = image,
                        contentDescription = "studioImage",
                        contentScale = Crop
                    )
                }
            }

            BPMSpacer(height = 22.dp)

            Divider(color = GrayColor10)
        }
    }
}