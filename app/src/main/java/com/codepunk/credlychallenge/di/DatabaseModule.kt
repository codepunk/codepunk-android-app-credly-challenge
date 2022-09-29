package com.codepunk.credlychallenge.di

import android.content.Context
import androidx.room.Room
import com.codepunk.credlychallenge.data.local.dao.ShowDao
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
    fun provideRocketDao(database: TvShowDatabase): ShowDao {
        return database.showDao()
    }

}