package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.SearchResult
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import com.codepunk.credlychallenge.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface SearchShowsUseCase : suspend (String) -> Flow<Result<List<SearchResult>>>

fun searchShows(
    query: String,
    tvShowRepository: TvShowRepository
): Flow<Result<List<SearchResult>>> =
    tvShowRepository
        .searchShows(query)
        .map { shows ->
            resultOf { shows }
        }
        .catch { throwable ->
            this.emit(Result.failure(throwable))
        }
