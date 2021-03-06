package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.models.Cast
import com.rappi.android.models.Video
import com.rappi.android.network.service.MovieService
import com.rappi.android.room.TopRatedDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class TopRatedRepository constructor(
    private val movieService: MovieService,
    private val topRatedDao: TopRatedDao
) : Repository {

    init {
        Timber.d("Injection TopRatedRepository")
    }

    @WorkerThread
    fun loadTopRated(page: Int, success: () -> Unit, error: () -> Unit) = flow {
        var movies = topRatedDao.getMovieList(page) ?: return@flow
        if (movies.isEmpty()) {
            val response = movieService.fetchTopRatedPeople(page)
            response.suspendOnSuccess {
                movies = data.results
                movies.forEach { it.page = page }
                topRatedDao.insertMovieList(movies)
                emit(movies)
            }.onError {
                error()
            }.onException { error() }
        } else {
            emit(movies)
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadVideoList(id: Int) = flow<List<Video>> {
        val movie = topRatedDao.getMovie(id)
        var videos = movie.videos
        if (videos.isNullOrEmpty()) {
            movieService.fetchVideos(id)
                .suspendOnSuccess {
                    videos = data.results
                    movie.videos = videos
                    topRatedDao.updateMovie(movie)
                    emit(videos ?: listOf())
                }
        } else {
            emit(videos ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadCastList(id: Int) = flow<List<Cast>> {
        val movie = topRatedDao.getMovie(id)
        var casts = movie.casts
        if (casts.isNullOrEmpty()) {
            movieService.fetchCasts(id)
                .suspendOnSuccess {
                    casts = data.cast
                    movie.casts = casts
                    topRatedDao.updateMovie(movie)
                    emit(casts ?: listOf())
                }
        } else {
            emit(casts ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadMovieById(id: Int) = flow {
        val movie = topRatedDao.getMovie(id)
        emit(movie)
    }.flowOn(Dispatchers.IO)
}