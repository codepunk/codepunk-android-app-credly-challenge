package com.codepunk.credlychallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.credlychallenge.data.local.converter.Converters
import com.codepunk.credlychallenge.data.local.dao.*
import com.codepunk.credlychallenge.data.local.model.*

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        ShowLocal::class,
        EpisodeLocal::class,
        PersonLocal::class,
        CharacterLocal::class,
        CastEntryLocal::class
    ],
    version = DATABASE_VERSION
)
@TypeConverters(Converters::class)
abstract class TvShowDatabase : RoomDatabase() {
    abstract fun castEntryDao(): CastEntryDao
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun personDao(): PersonDao
    abstract fun showDao(): ShowDao
}