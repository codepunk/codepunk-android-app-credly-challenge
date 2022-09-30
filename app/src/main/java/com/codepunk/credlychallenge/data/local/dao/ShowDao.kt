package com.codepunk.credlychallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.credlychallenge.data.local.model.ShowLocal

@Dao
interface ShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(show: ShowLocal)

    @Delete
    suspend fun delete(show: ShowLocal)

    @Query("SELECT * FROM show WHERE id=:id")
    suspend fun getShow(id: Int): ShowLocal?

}