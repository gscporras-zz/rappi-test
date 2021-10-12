package com.rappi.android.di

import android.content.Context
import androidx.room.Room
import com.rappi.android.room.AppDatabase
import com.rappi.android.room.MovieDao
import com.rappi.android.room.PeopleDao
import com.rappi.android.room.TvDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "MovieCompose.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(appDatabase: AppDatabase): TvDao {
        return appDatabase.tvDao()
    }

    @Provides
    @Singleton
    fun providePeopleDao(appDatabase: AppDatabase): PeopleDao {
        return appDatabase.peopleDao()
    }
}