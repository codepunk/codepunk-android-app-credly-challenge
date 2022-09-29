package com.codepunk.credlychallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.credlychallenge.data.local.converters.Converters
import com.codepunk.credlychallenge.data.local.dao.ShowDao
import com.codepunk.credlychallenge.data.local.model.ShowLocal

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        ShowLocal::class
    ],
    version = DATABASE_VERSION
)
@TypeConverters(Converters::class)
abstract class TvShowDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
}