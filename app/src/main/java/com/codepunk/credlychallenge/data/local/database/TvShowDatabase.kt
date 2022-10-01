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

package com.codepunk.credlychallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.credlychallenge.data.local.converter.Converters
import com.codepunk.credlychallenge.data.local.dao.*
import com.codepunk.credlychallenge.data.local.model.*

private const val DATABASE_VERSION = 1

/**
 * The SQLite database that will be used to store show, episode and cast information.
 */
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

    // region Methods

    abstract fun castEntryDao(): CastEntryDao
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun personDao(): PersonDao
    abstract fun showDao(): ShowDao

    // endregion Methods

}