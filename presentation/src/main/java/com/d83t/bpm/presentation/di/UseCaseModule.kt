package com.d83t.bpm.presentation.di

import com.d83t.bpm.domain.repository.MainRepository
import com.d83t.bpm.domain.usecase.GetSampleTextUseCase
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

}