package com.codepunk.credlychallenge.di

import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.domain.usecase.GetShowsUseCase
import com.codepunk.credlychallenge.domain.usecase.SearchShowsUseCase
import com.codepunk.credlychallenge.domain.usecase.getShows
import com.codepunk.credlychallenge.domain.usecase.searchShows
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesGetShowsUseCase(
        tvShowRepository: TvShowRepository
    ): GetShowsUseCase = GetShowsUseCase { ids ->
        getShows(ids, tvShowRepository)
    }

    @Provides
    fun provideSearchShowsUseCase(
        tvShowRepository: TvShowRepository
    ): SearchShowsUseCase = SearchShowsUseCase { query ->
        searchShows(query, tvShowRepository)
    }

}