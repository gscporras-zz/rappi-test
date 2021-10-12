package com.rappi.android.di

import android.content.Context
import androidx.room.Room
import com.rappi.android.room.AppDatabase
import com.rappi.android.room.MovieDao
import com.rappi.android.room.TopRatedDao
import com.rappi.android.room.PopularDao
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
    fun providePopularDao(appDatabase: AppDatabase): PopularDao {
        return appDatabase.popularDao()
    }

    @Provides
    @Singleton
    fun provideTopRatedDao(appDatabase: AppDatabase): TopRatedDao {
        return appDatabase.topRatedDao()
    }
}