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
import com.codepunk.credlychallenge.data.local.model.CastEntryLocal

/**
 * A [Dao] for querying, inserting and deleting cast data into the local database.
 */
@Dao
interface CastEntryDao {

    // region Methods

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(castEntry: CastEntryLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cast: List<CastEntryLocal>)

    @Delete
    suspend fun delete(castEntry: CastEntryLocal)

    @Query("SELECT * FROM `cast` WHERE show_id=:showId")
    suspend fun getCastForShow(showId: Int): List<CastEntryLocal>

    // endregion Methods

}