package com.d83t.bpm.domain.usecase

import com.d83t.bpm.domain.repository.MainRepository
import javax.inject.Inject

class GetSampleTextUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): String {
        return mainRepository.getSampleText()
    }
}