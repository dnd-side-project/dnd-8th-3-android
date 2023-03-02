package com.d83t.bpm.domain.usecase.splash

import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserInfo
import com.d83t.bpm.domain.repository.SplashRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@Reusable
class SendKakaoUserIdVerificationUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    suspend operator fun invoke(kakaoUserId: Long): Flow<ResponseState<UserInfo>> {
        return splashRepository.sendSignIn(kakaoUserId)
    }
}