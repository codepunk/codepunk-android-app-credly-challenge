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

import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Use case for getting episodes related to a show.
 */

fun interface GetEpisodesUseCase : suspend (Int) -> Flow<Result<List<Episode>>>

fun getEpisodes(
    showId: Int,
    tvShowRepository: TvShowRepository
): Flow<Result<List<Episode>>> =
    tvShowRepository
        .getEpisodes(showId)
        .map { episodes ->
            resultOf { episodes }
        }
        .catch { emit(Result.failure(it)) }
