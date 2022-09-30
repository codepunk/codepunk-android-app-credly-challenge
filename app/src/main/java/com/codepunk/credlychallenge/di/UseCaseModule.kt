package com.codepunk.credlychallenge.di

import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetShowUseCase(
        tvShowRepository: TvShowRepository
    ): GetShowUseCase = GetShowUseCase { showId ->
        getShow(showId, tvShowRepository)
    }

    @Provides
    fun provideGetShowsUseCase(
        tvShowRepository: TvShowRepository
    ): GetShowsUseCase = GetShowsUseCase { ids ->
        getShows(ids, tvShowRepository)
    }

    @Provides
    fun provideGetEpisodesUseCase(
        tvShowRepository: TvShowRepository
    ): GetEpisodesUseCase = GetEpisodesUseCase { showId ->
        getEpisodes(showId, tvShowRepository)
    }

    @Provides
    fun provideGetCastUseCase(
        tvShowRepository: TvShowRepository
    ): GetCastUseCase = GetCastUseCase { showId ->
        getCast(showId, tvShowRepository)
    }

}