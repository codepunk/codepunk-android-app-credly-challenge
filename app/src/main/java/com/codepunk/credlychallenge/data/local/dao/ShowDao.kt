package com.codepunk.credlychallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.credlychallenge.data.local.model.ShowLocal

@Dao
interface ShowDao {

    @Query("SELECT * FROM show WHERE imdb=:imdb")
    suspend fun getShow(imdb: String): ShowLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShow(show: ShowLocal)

}