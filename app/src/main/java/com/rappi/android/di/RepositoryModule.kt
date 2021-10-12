package com.rappi.android.di

import com.rappi.android.network.service.MovieService
import com.rappi.android.repository.MovieRepository
import com.rappi.android.repository.TopRatedRepository
import com.rappi.android.repository.PopularRepository
import com.rappi.android.room.MovieDao
import com.rappi.android.room.TopRatedDao
import com.rappi.android.room.PopularDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(
        movieService: MovieService,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepository(movieService, movieDao)
    }

    @Provides
    @ViewModelScoped
    fun providePopularRepository(
        movieService: MovieService,
        popularDao: PopularDao
    ): PopularRepository {
        return PopularRepository(movieService, popularDao)
    }

    @Provides
    @ViewModelScoped
    fun provideTopRatedRepository(
        movieService: MovieService,
        topRatedDao: TopRatedDao
    ): TopRatedRepository {
        return TopRatedRepository(movieService, topRatedDao)
    }
}