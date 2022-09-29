package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface GetShowsUseCase : suspend (List<String>) -> Flow<Result<List<Show>>>

fun getShows(
    imdbList: List<String>,
    tvShowRepository: TvShowRepository
): Flow<Result<List<Show>>> =
    tvShowRepository
        .getShows(imdbList)
        .map { shows ->
            resultOf { shows }
        }
        .catch { throwable ->
            this.emit(Result.failure(throwable))
        }