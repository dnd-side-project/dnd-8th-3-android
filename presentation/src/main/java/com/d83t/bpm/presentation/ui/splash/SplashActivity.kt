package com.d83t.bpm.presentation.ui.splash

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.main.MainActivity
import com.d83t.bpm.presentation.ui.sign_up.SignUpActivity
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import com.d83t.bpm.presentation.util.showDebugToast
import com.d83t.bpm.presentation.util.showToast
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseComponentActivity() {

    override val viewModel: SplashViewModel by viewModels()

    private val startButtonVisibilityState = mutableStateOf(false)

    private val kakaoLoginInstance: UserApiClient by lazy {
        UserApiClient.instance
    }

    override fun initUi() {
        setContent {
            BPMTheme {
                SplashActivityContent(
                    startButtonVisibilityState = startButtonVisibilityState,
                    onClickStartButton = {
                        setupLogin()
                    }
                )
            }
        }
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.state.collectLatest { state ->
                when (state) {
                    SplashState.Init -> {
                        MainScope().launch {
                            delay(1000L)
                            viewModel.getStoredUserInfo()
                        }
                    }
                    is SplashState.ValidationCheck -> {
                        viewModel.sendKakaoIdVerification(state.id, state.kakaoNickName)
                    }
                    is SplashState.SignUp -> {
                        goToSignUp(state.id, state.kakaoNickName)
                    }
                    SplashState.NoUserInfo -> {
                        startButtonVisibilityState.value = true
                    }
                    is SplashState.SaveToken -> {
                        // TODO : 캡슐화에 대한 생각이 추가되어야 할 듯
                        state.token?.let { viewModel.saveUserToken(it) }
                    }
                    SplashState.Finish -> {
                        goToMainActivity()
                    }
                    is SplashState.Error -> {
                        this@SplashActivity.showToast("로그인 중 오류가 발생하였습니다. 다시 시도해주세요")
                    }
                }
            }
        }
    }

    private fun setupLogin() {
        // 카카오톡으로 로그인
        if (kakaoLoginInstance.isKakaoTalkLoginAvailable(this)) {
            kakaoLoginInstance.loginWithKakaoTalk(this) { loginInfo, error ->
                if (error != null) {
                    // 로그인 실패
//                    showDebugToast("login failed! cause : ${error.message}")
                    showToast("로그인에 실패하였습니다. 다시 시도해 주세요.")
                } else if (loginInfo != null) {
                    // 로그인 성공
                    kakaoLoginInstance.me { user, error ->
                        viewModel.setKakaoUserId(user?.id ?: 0L, user?.kakaoAccount?.profile?.nickname ?: "")
//                        user?.id?.let { viewModel.setKakaoUserId(it) }
//                        showDebugToast("login succeed. user token : ${user?.id}")
                    }
                }
            }
        } else {
            showToast("로그인에 실패하였습니다. 다시 시도해 주세요.")
        }
    }

    private fun goToSignUp(
        kakaoUserId: Long?,
        kakaoNickName: String
    ) {
        startActivity(
            SignUpActivity.newIntent(this, kakaoUserId, kakaoNickName)
        )
        finish()
    }

    private fun goToMainActivity() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }
}

@Composable
private fun SplashActivityContent(
    startButtonVisibilityState: MutableState<Boolean>,
    onClickStartButton: () -> Unit
) {
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

    val startButtonAlphaState = animateFloatAsState(
        targetValue = if (startButtonVisibilityState.value) 1f else 0f,
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
                fontFamily = pyeongchangPeace,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                letterSpacing = 0.8.sp,
                color = Color.White
            )

            Image(
                modifier = Modifier.alpha(logoAlphaState.value),
                painter = painterResource(id = R.drawable.logo_splash),
                contentDescription = "splashLogo"
            )
        }

        Box(modifier = Modifier
            .alpha(startButtonAlphaState.value)
            .padding(bottom = 30.dp)
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFFFEE500))
            .align(BottomCenter)
            .clickable {
                onClickStartButton()
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
                modifier = Modifier.align(Center),
                text = "카카오톡으로 시작하기",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val startButtonVisibilityState = remember { mutableStateOf(false) }
    SplashActivityContent(
        startButtonVisibilityState = startButtonVisibilityState,
        onClickStartButton = {}
    )
}