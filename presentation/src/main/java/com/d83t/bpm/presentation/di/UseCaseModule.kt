package com.d83t.bpm.presentation.di

import com.d83t.bpm.domain.repository.MainRepository
import com.d83t.bpm.domain.repository.SplashRepository
import com.d83t.bpm.domain.usecase.GetSampleTextUseCase
import com.d83t.bpm.domain.usecase.GetStoredIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetSampleTextUseCase(mainRepository: MainRepository): GetSampleTextUseCase {
        return GetSampleTextUseCase(mainRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetStoredIdUseCase(splashRepository: SplashRepository): GetStoredIdUseCase {
        return GetStoredIdUseCase(splashRepository)
    }
}