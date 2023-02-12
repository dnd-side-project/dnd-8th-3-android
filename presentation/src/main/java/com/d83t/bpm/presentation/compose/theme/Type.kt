package com.d83t.bpm.presentation.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val BPMTypography = Typography(
    h1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    // body
    button = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    caption = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        letterSpacing = 0.sp
    ), // for tab name
    overline = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        letterSpacing = 0.sp
    ) // for type limit
)