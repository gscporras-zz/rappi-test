package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.models.Cast
import com.rappi.android.models.Video
import com.rappi.android.network.service.SearchService
import com.rappi.android.room.SearchDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class SearchRepository constructor(
    private val searchService: SearchService,
    private val searchDao: SearchDao
) {

    init {
        Timber.d("Injection SearchRepository")
    }

    @WorkerThread
    fun loadMovies(query: String?, page: Int, success: () -> Unit, error: () -> Unit) = flow {
        var movies = searchDao.getMovieList(query, page)
        if (movies.isEmpty()) {
            val response = searchService.fetchSearchedMovie(query, page)
            response.suspendOnSuccess {
                movies = data.results
                movies.forEach { it.page = page }
                searchDao.insertMovieList(movies)
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
        val movie = searchDao.getMovie(id)
        var videos = movie.videos
        if (videos.isNullOrEmpty()) {
            searchService.fetchVideos(id)
                .suspendOnSuccess {
                    videos = data.results
                    movie.videos = videos
                    searchDao.updateMovie(movie)
                    emit(videos ?: listOf())
                }
        } else {
            emit(videos ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadCastList(id: Int) = flow<List<Cast>> {
        val movie = searchDao.getMovie(id)
        var casts = movie.casts
        if (casts.isNullOrEmpty()) {
            searchService.fetchCasts(id)
                .suspendOnSuccess {
                    casts = data.cast
                    movie.casts = casts
                    searchDao.updateMovie(movie)
                    emit(casts ?: listOf())
                }
        } else {
            emit(casts ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadMovieById(id: Int) = flow {
        val movie = searchDao.getMovie(id)
        emit(movie)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadPersonById(id: Int) = flow {
        val person = searchDao.getMovie(id)
        emit(person)
    }.flowOn(Dispatchers.IO)
}