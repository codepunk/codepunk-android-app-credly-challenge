package com.codepunk.credlychallenge.domain.repository

import com.codepunk.credlychallenge.domain.model.SearchResult
import com.codepunk.credlychallenge.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun searchShows(query: String): Flow<List<SearchResult>>

    fun getShows(ids: List<Int>): Flow<List<Show>>

}