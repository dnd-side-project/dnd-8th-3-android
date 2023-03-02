package com.d83t.bpm.presentation.ui.splash

import com.d83t.bpm.domain.model.NetworkError
import com.d83t.bpm.presentation.util.ComposeUiState

sealed interface SplashState : ComposeUiState {

    // 로그인 플로우
    // 화면 진입 -> 기존 저장된 값 체크
    // 저장값 있을 경우 -> 메인 페이지로 이동
    // 저장값 없을 경우 -> 페이지 남기
    // validation 체크 성공 -> 토큰 받아 메인 페이지로 이동
    // validation 체크 실패(미가입자) -> SignUp 페이지로 이동

    object Init : SplashState

    // 로그인 실패 (이후 로그인 버튼 클릭)
    object NoUserInfo : SplashState

    // 카카오 유저 정보 가져오기 성공
    // 이후 Validation Check 진행
    data class ValidationCheck(val id: Long) : SplashState

    // Validation 완료 (신규 유저)
    // SignUp 페이지 이동
    data class SignUp(val id: Long?) : SplashState

    // 유저 토큰 저장
    data class SaveToken(val token: String?) : SplashState

    object Finish : SplashState

    data class Error(val errorRes: NetworkError) : SplashState
}