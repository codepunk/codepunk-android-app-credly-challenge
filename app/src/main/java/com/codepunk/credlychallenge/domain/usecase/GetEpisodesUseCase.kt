package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

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