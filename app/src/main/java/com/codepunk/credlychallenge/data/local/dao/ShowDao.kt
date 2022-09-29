package com.codepunk.credlychallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.credlychallenge.data.local.model.ShowLocal

@Dao
interface ShowDao {

    @Query("SELECT * FROM show WHERE id=:id")
    suspend fun getShow(id: Int): ShowLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShow(show: ShowLocal)

}