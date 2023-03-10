package com.d83t.bpm.domain.usecase.splash

import com.d83t.bpm.domain.repository.SplashRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SetKakaoUserIdUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    suspend operator fun invoke(kakaoUserId: Long): Flow<Long?> {
    return splashRepository.setKakaoUserId(kakaoUserId)
    }
}