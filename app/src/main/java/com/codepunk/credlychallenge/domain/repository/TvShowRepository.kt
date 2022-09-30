package com.codepunk.credlychallenge.domain.repository

import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getShow(id: Int): Flow<Show?>

    fun getShows(ids: List<Int>): Flow<List<Show>>

    fun getEpisodes(id: Int): Flow<List<Episode>>

    fun getCast(id: Int): Flow<List<CastEntry>>

}