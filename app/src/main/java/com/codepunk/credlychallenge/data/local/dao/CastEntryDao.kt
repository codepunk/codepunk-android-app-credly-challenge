package com.codepunk.credlychallenge.data.local.dao

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.CastEntryLocal

@Dao
interface CastEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(castEntry: CastEntryLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cast: List<CastEntryLocal>)

    @Delete
    suspend fun delete(castEntry: CastEntryLocal)

    @Query("SELECT * FROM `cast` WHERE show_id=:showId")
    suspend fun getCastForShow(showId: Int): List<CastEntryLocal>

}