package com.d83t.bpm.presentation.di

import com.d83t.bpm.data.datastore.DataStoreManager
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.data.repositoryImpl.MainRepositoryImpl
import com.d83t.bpm.data.repositoryImpl.SignUpRepositoryImpl
import com.d83t.bpm.data.repositoryImpl.SplashRepositoryImpl
import com.d83t.bpm.domain.repository.MainRepository
import com.d83t.bpm.domain.repository.SignUpRepository
import com.d83t.bpm.domain.repository.SplashRepository
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

    @Singleton
    @Provides
    fun provideSplashRepository(
        dataStoreManager: DataStoreManager
    ): SplashRepository {
        return SplashRepositoryImpl(dataStoreManager)
    }

    @Singleton
    @Provides
    fun provideSignUpRepository(
        mainApi: MainApi
    ): SignUpRepository {
        return SignUpRepositoryImpl(mainApi)
    }
}