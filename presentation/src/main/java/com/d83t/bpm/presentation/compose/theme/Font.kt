package com.d83t.bpm.presentation.compose.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.d83t.bpm.presentation.R

val pretendard = FontFamily(
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.pretendard_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.pretendard_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

val pyeongchang = FontFamily(
    Font(
        resId = R.font.pyeongchang_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.pyeongchang_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

val pyeongchangPeace = FontFamily(
    Font(
        resId = R.font.pyeongchang_peace_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    )
)