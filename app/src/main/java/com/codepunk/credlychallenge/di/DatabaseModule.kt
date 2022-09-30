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

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

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

}