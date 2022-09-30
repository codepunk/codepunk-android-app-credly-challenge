package com.codepunk.credlychallenge.domain.repository

import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getShow(showId: Int): Flow<Show?>

    fun getShows(showIds: List<Int>): Flow<List<Show>>

    fun getEpisodes(showId: Int): Flow<List<Episode>>

    fun getCast(showId: Int): Flow<List<CastEntry>>

}