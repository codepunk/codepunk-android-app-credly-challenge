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

package com.codepunk.credlychallenge.data.local.dao;

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.CharacterLocal

/**
 * A [Dao] for querying, inserting and deleting character data into the local database.
 */
@Dao
interface CharacterDao {

    // region Methods

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<CharacterLocal>)

    @Delete
    suspend fun delete(character: CharacterLocal)

    @Query("SELECT * FROM character WHERE id=:characterId")
    suspend fun getCharacter(characterId: Int): CharacterLocal?

    @Query("SELECT * FROM character INNER JOIN `cast` ON character.id = `cast`.character_id WHERE `cast`.show_id = :showId")
    suspend fun getCharactersInShow(showId: Int): List<CharacterLocal>

    // endregion Methods

}
