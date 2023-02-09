package com.d83t.bpm.presentation.di

import com.d83t.bpm.data.repositoryImpl.MainRepositoryImpl
import com.d83t.bpm.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
    ): MainRepository {
        return MainRepositoryImpl()
    }

}