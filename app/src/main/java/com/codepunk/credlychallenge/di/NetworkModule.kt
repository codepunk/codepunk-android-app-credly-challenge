package com.codepunk.credlychallenge.di

import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.data.remote.api.TvShowApi
import com.codepunk.credlychallenge.data.repository.TvShowRepositoryImpl
import com.codepunk.credlychallenge.di.NetworkModule.BindsModule
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BindsModule::class])
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        return Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.TVMAZE_API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesTvShowApi(
        retrofit: Retrofit
    ): TvShowApi = retrofit.create(TvShowApi::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindsTvShowRepository(impl: TvShowRepositoryImpl): TvShowRepository

    }

}