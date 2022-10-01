/*
 * Copyright (C) 2022 Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codepunk.credlychallenge.di

import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dependency Injection instances for use-case-related classes.
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    // region Methods

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

    // endregion Methods

}
