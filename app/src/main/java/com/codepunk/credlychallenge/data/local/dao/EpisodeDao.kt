package com.codepunk.credlychallenge.data.local.dao

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.EpisodeLocal

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: EpisodeLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episodes: List<EpisodeLocal>)

    @Delete
    suspend fun delete(episode: EpisodeLocal)

    @Query("SELECT * FROM episode WHERE id=:id")
    suspend fun getEpisode(id: Int): EpisodeLocal?

    @Query("SELECT * FROM episode WHERE show_id=:showId ORDER BY season, number")
    suspend fun getEpisodesForShow(showId: Int): List<EpisodeLocal>

}