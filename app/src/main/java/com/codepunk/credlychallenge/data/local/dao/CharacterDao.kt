package com.codepunk.credlychallenge.data.local.dao;

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.CharacterLocal

@Dao
interface CharacterDao {

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

}
