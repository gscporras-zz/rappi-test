package com.rappi.android.di

import com.rappi.android.network.service.MovieService
import com.rappi.android.network.service.PeopleService
import com.rappi.android.network.service.TheDiscoverService
import com.rappi.android.network.service.TvService
import com.rappi.android.repository.DiscoverRepository
import com.rappi.android.repository.MovieRepository
import com.rappi.android.repository.PeopleRepository
import com.rappi.android.repository.TvRepository
import com.rappi.android.room.MovieDao
import com.rappi.android.room.PeopleDao
import com.rappi.android.room.TvDao
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
    fun provideDiscoverRepository(
        discoverService: TheDiscoverService,
        movieDao: MovieDao,
        tvDao: TvDao
    ): DiscoverRepository {
        return DiscoverRepository(discoverService, movieDao, tvDao)
    }

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
    fun providePeopleRepository(
        peopleService: PeopleService,
        peopleDao: PeopleDao
    ): PeopleRepository {
        return PeopleRepository(peopleService, peopleDao)
    }

    @Provides
    @ViewModelScoped
    fun provideTvRepository(
        tvService: TvService,
        tvDao: TvDao
    ): TvRepository {
        return TvRepository(tvService, tvDao)
    }
}