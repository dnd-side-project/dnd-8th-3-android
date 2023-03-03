package com.d83t.bpm.presentation.di

import com.d83t.bpm.data.datastore.DataStoreManager
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.data.repositoryImpl.*
import com.d83t.bpm.domain.repository.*
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
        dataStoreManager: DataStoreManager,
        mainApi: MainApi
    ): SplashRepository {
        return SplashRepositoryImpl(dataStoreManager, mainApi)
    }

    @Singleton
    @Provides
    fun provideSignUpRepository(
        mainApi: MainApi
    ): SignUpRepository {
        return SignUpRepositoryImpl(mainApi)
    }

    @Singleton
    @Provides
    fun provideScheduleRepository(
        mainApi: MainApi
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(mainApi)
    }

    @Singleton
    @Provides
    fun provideStudioDetailRepository(
        mainApi: MainApi
    ): StudioDetailRepository {
        return StudioDetailRepositoryImpl(mainApi)
    }

    @Singleton
    @Provides
    fun provideReviewRepository(
        mainApi: MainApi
    ): ReviewRepository {
        return ReviewRepositoryImpl(mainApi)
    }

    @Singleton
    @Provides
    fun provideWriteReviewRepository(
        mainApi: MainApi
    ): WriteReviewRepository {
        return WriteReviewRepositoryImpl(mainApi)
    }
}