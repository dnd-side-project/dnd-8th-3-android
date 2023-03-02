package com.d83t.bpm.domain.usecase.splash

import com.d83t.bpm.domain.repository.SplashRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@Reusable
class SetUserTokenUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    suspend operator fun invoke(userToken: String): Flow<String?> {
        return splashRepository.setUserToken(userToken)
    }
}