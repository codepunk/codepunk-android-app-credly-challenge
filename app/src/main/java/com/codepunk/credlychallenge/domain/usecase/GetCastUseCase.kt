package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface GetCastUseCase : suspend (Int) -> Flow<Result<List<CastEntry>>>

fun getCast(
    showId: Int,
    tvShowRepository: TvShowRepository
): Flow<Result<List<CastEntry>>> =
    tvShowRepository
        .getCast(showId)
        .map { cast ->
            resultOf { cast }
        }
        .catch { emit(Result.failure(it)) }