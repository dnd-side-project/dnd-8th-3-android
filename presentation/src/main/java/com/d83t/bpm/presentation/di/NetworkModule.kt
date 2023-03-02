package com.d83t.bpm.presentation.di

import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.presentation.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val BASE_URL = "http://3.36.240.13:8080/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
//            client.addNetworkInterceptor(
//                FlipperOkhttpInterceptor(
//                    AndroidFlipperClient.getInstance(App().applicationContext).getPlugin(
//                        NetworkFlipperPlugin.ID
//                    )
//                )
//            )
            client.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })

            client.addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val shouldBeAuthorized = request.header(name = "shouldBeAuthorized") != "false"
                val requestBuilder = request.newBuilder()

                if (shouldBeAuthorized) {
                    requestBuilder.addHeader(
                        name = "Authorization",
                        value = "Token eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6InJvb3QiLCJpYXQiOjE2Nzc3NjgxMTUsImV4cCI6MTY4MDc2ODExNX0._INkoO0kz7Yiwb8WsgQ_q5ySmFRe17hxYxICl9wRnjM" // forTest
                    )

                } else {
                    requestBuilder.removeHeader(name = "shouldBeAuthorized")
                }

                return@Interceptor chain.proceed(request = requestBuilder.build())
            })

        } else {
            client.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
            })
        }

        return client.build()
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}