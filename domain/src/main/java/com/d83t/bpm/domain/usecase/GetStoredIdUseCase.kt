package com.d83t.bpm.domain.usecase

import com.d83t.bpm.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoredIdUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    operator fun invoke(): Flow<String> {
        return splashRepository.getStoredId()
    }
}