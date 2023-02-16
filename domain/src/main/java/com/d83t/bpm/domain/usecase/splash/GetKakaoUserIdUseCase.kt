package com.d83t.bpm.domain.usecase.splash

import com.d83t.bpm.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKakaoUserIdUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    operator fun invoke(): Flow<String?> {
        return splashRepository.getKakaoUserId()
    }
}