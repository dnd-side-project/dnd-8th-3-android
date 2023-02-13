package com.d83t.bpm.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.theme.BPMShapes
import com.d83t.bpm.presentation.compose.theme.BPMTypography
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi {
            SplashActivityContent()
        }
    }

}

@Composable
private fun SplashActivityContent() {
    val subtitleVisibilityState = remember { mutableStateOf(false) }
    val subtitleAlphaState = animateFloatAsState(
        targetValue = if (subtitleVisibilityState.value) 1f else 0f,
        animationSpec = tween(1000)
    )

    val logoVisibilityState = remember { mutableStateOf(false) }
    val logoAlphaState = animateFloatAsState(
        targetValue = if (logoVisibilityState.value) 1f else 0f,
        animationSpec = tween(1000)
    )

    LaunchedEffect(key1 = Unit) {
        delay(200L)
        subtitleVisibilityState.value = true
        delay(400L)
        logoVisibilityState.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.bg_splash),
            contentDescription = "splashBackground",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 30.dp)
                .height((LocalConfiguration.current.screenHeightDp / 2).dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .alpha(subtitleAlphaState.value),
                text = "당신을 위한\n바디프로필 매니저",
                style = BPMTypography.h5,
                color = Color.White
            )

            Image(
                modifier = Modifier.alpha(logoAlphaState.value),
                painter = painterResource(id = R.drawable.logo_splash),
                contentDescription = "splashLogo"
            )
        }

        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .padding(horizontal = 16.dp)
                .clip(shape = BPMShapes.small)
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFFEE500))
                .align(Alignment.BottomCenter)
                .clickable {
                    // todo : onClickKakaoLogin
                }
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 18.dp)
                    .size(24.dp)
                    .align(CenterStart),
                painter = painterResource(id = R.drawable.ic_kakao),
                contentDescription = "kakaoIcon"
            )

            Text(
                modifier = Modifier
                    .align(Center),
                text = "카카오톡으로 시작하기",
                style = BPMTypography.body1,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SplashActivityContent()
}