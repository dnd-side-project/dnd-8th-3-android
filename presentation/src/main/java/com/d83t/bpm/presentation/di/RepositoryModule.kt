package com.d83t.bpm.presentation.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.d83t.bpm.data.repositoryImpl.MainRepositoryImpl
import com.d83t.bpm.data.repositoryImpl.SplashRepositoryImpl
import com.d83t.bpm.domain.repository.MainRepository
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
        dataStore: DataStore<Preferences>
    ): SplashRepository {
        return SplashRepositoryImpl(dataStore)
    }
}