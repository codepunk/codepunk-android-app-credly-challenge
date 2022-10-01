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

package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting a list of shows.
 */

fun interface GetShowsUseCase : suspend (List<Int>) -> Flow<Result<List<Show>>>

fun getShows(
    showIds: List<Int>,
    tvShowRepository: TvShowRepository
): Flow<Result<List<Show>>> = resultOf { tvShowRepository.getShows(showIds) }
