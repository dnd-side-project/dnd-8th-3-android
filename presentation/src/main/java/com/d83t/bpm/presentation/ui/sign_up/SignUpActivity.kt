package com.d83t.bpm.presentation.ui.sign_up

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d83t.bpm.presentation.R
import com.d83t.bpm.presentation.base.BaseComponentActivity
import com.d83t.bpm.presentation.compose.*
import com.d83t.bpm.presentation.compose.theme.*
import com.d83t.bpm.presentation.ui.main.MainActivity
import com.d83t.bpm.presentation.util.convertUriToBitmap
import com.d83t.bpm.presentation.util.repeatCallDefaultOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseComponentActivity() {
    override val viewModel: SignUpViewModel by viewModels()

    private lateinit var imageLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private val imageState = mutableStateOf<ImageBitmap?>(null)
    private val nicknameTextState = mutableStateOf("")
    private val bioTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { _ ->
                try {
                    imageState.value = (convertUriToBitmap(
                        contentResolver = contentResolver,
                        uri = uri
                    ).asImageBitmap())
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
                nicknameTextState.value = viewModel.kakaoUserInfo.second

                SignUpActivityContent(
                    imageState = imageState,
                    nicknameTextState = nicknameTextState,
                    bioTextState = bioTextState,
                    onClickGetImage = { imageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    onClickSignUp = { viewModel.onClickSignUp() }
                )

                viewModel.state.collectAsStateWithLifecycle().value.also { state ->
                    when (state) {
                        is SignUpState.Init -> Unit
                        is SignUpState.Loading -> LoadingScreen()
                        is SignUpState.SignUpSuccess -> goToMainActivity()
                        is SignUpState.Error -> Unit
                    }
                }
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(MainActivity.newIntent(this@SignUpActivity))
        finish()
    }

    override fun setupCollect() {
        repeatCallDefaultOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    SignUpViewEvent.SignUp -> {
                        viewModel.signUp(
                            kakaoId = 960422L, // kakao id
                            nickname = nicknameTextState.value,
                            bio = bioTextState.value,
                            image = imageState.value ?: BitmapFactory.decodeResource(this@SignUpActivity.resources, R.drawable.default_profile_image).asImageBitmap()
                        )
                    }
                }
            }
        }
    }

    companion object {

        const val KEY_BUNDLE = "bundle"
        const val KEY_KAKAO_USER_ID = "kakao_user_id"
        const val KEY_KAKAO_NICK_NAME = "kakao_nickname"

        fun newIntent(context: Context, kakaoUserId: Long?, kakaoNickName : String): Intent {
            return Intent(context, SignUpActivity::class.java).apply {
                putExtra(KEY_BUNDLE, bundleOf(
                    KEY_KAKAO_USER_ID to kakaoUserId,
                    KEY_KAKAO_NICK_NAME to kakaoNickName
                ))
            }
        }

    }
}

@Composable
private inline fun SignUpActivityContent(
    imageState: MutableState<ImageBitmap?>,
    nicknameTextState: MutableState<String>,
    bioTextState: MutableState<String>,
    crossinline onClickGetImage: () -> Unit,
    crossinline onClickSignUp: () -> Unit
) {
    val omissionState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(header = "프로필 생성하기")

            BPMSpacer(height = 44.dp)

            Column(
                modifier = Modifier.align(CenterHorizontally),
                horizontalAlignment = CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(130.dp)
                        .align(CenterHorizontally),
                    bitmap = imageState.value ?: BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.default_profile_image).asImageBitmap(),
                    contentDescription = "profileImage",
                    contentScale = Crop
                )

                BPMSpacer(height = 16.dp)

                Text(
                    modifier = Modifier.clickable { onClickGetImage() },
                    text = "프로필 사진 등록",
                    fontWeight = Medium,
                    fontSize = 15.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.Underline,
                    color = GrayColor12
                )
            }

            BPMSpacer(height = 50.dp)

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ProfileTextField(
                    isEssential = true,
                    title = "닉네임",
                    hint = "어깨_매니저",
                    textState = nicknameTextState,
                    omissionState = omissionState
                )

                BPMSpacer(height = 24.dp)

                ProfileTextField(
                    isEssential = false,
                    title = "한줄 소개",
                    hint = "회원님 반갑습니다. 제 특기는 어깨춤 추기입니다.",
                    textState = bioTextState
                )
            }
        }

        RoundedCornerButton(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 13.dp
                )
                .fillMaxWidth()
                .height(48.dp)
                .align(BottomCenter),
            text = "저장하기",
            textColor = if (nicknameTextState.value.isNotEmpty()) Color.Black else GrayColor7,
            buttonColor = if (nicknameTextState.value.isNotEmpty()) MainGreenColor else GrayColor11,
            onClick = {
                if (nicknameTextState.value.isEmpty()) {
                    omissionState.value = true
                } else {
                    onClickSignUp()
                }
            }
        )
    }
}

@Composable
private fun ProfileTextField(
    isEssential: Boolean,
    title: String,
    hint: String,
    textState: MutableState<String>,
    omissionState: MutableState<Boolean>? = null
) {
    Column {
        Row(modifier = Modifier.padding(start = 2.dp)) {
            Text(
                text = title,
                fontWeight = Medium,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )

            if (isEssential) {
                Text(
                    text = "*",
                    fontWeight = Medium,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    color = Color.Red
                )
            }
        }

        BPMSpacer(height = 10.dp)

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(42.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = if (textState.value.isNotEmpty()) Color.Black else if (omissionState?.value == true) Color.Red else GrayColor7
                )
        ) {
            TextFieldColorProvider {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                        .align(Center),
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    textStyle = TextStyle(
                        fontWeight = Medium,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    singleLine = true
                )

                if (textState.value.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .fillMaxWidth()
                            .align(Center),
                        text = hint,
                        fontWeight = Medium,
                        fontSize = 13.sp,
                        letterSpacing = 0.sp,
                        color = GrayColor7,
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    )
                }
            }
        }

        if (omissionState?.value == true) {
            BPMSpacer(height = 6.dp)

            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = "필수 입력 정보 입니다.",
                fontWeight = Medium,
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                color = Color.Red
            )
        }
    }
}