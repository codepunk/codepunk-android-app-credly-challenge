package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface GetShowUseCase : suspend (Int) -> Flow<Result<Show?>>

fun getShow(
    showId: Int,
    tvShowRepository: TvShowRepository
): Flow<Result<Show?>> {
    return tvShowRepository
        .getShow(showId)
        .map { show ->
            resultOf { show }
        }
        .catch { emit(Result.failure(it)) }
}