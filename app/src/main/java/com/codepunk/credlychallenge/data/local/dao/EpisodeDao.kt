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

package com.codepunk.credlychallenge.data.local.dao

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.EpisodeLocal

/**
 * A [Dao] for querying, inserting and deleting episode data into the local database.
 */
@Dao
interface EpisodeDao {

    // region Methods

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

    // endregion Methods

}