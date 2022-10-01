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

package com.codepunk.credlychallenge.di

import android.content.Context
import androidx.room.Room
import com.codepunk.credlychallenge.data.local.dao.*
import com.codepunk.credlychallenge.data.local.database.TvShowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "tv_show_database"

/**
 * Dependency Injection instances for database-related classes.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    // region Methods

    @Singleton
    @Provides
    fun provideTvShowDatabase(
        @ApplicationContext context: Context
    ): TvShowDatabase {
        return Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCastEntryDao(database: TvShowDatabase): CastEntryDao {
        return database.castEntryDao()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(database: TvShowDatabase): CharacterDao {
        return database.characterDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeDao(database: TvShowDatabase): EpisodeDao {
        return database.episodeDao()
    }

    @Singleton
    @Provides
    fun providePersonDao(database: TvShowDatabase): PersonDao {
        return database.personDao()
    }

    @Singleton
    @Provides
    fun provideShowDao(database: TvShowDatabase): ShowDao {
        return database.showDao()
    }

    // endregion Methods

}
