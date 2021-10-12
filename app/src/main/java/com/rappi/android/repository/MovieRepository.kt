package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.models.Cast
import com.rappi.android.models.Keyword
import com.rappi.android.models.Review
import com.rappi.android.models.Video
import com.rappi.android.models.entities.Movie
import com.rappi.android.network.service.MovieService
import com.rappi.android.room.MovieDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class MovieRepository constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) : Repository {

    init {
        Timber.d("Injection MovieRepository")
    }

    @WorkerThread
    fun loadMovies(page: Int, success: () -> Unit, error: () -> Unit) = flow {
        var movies = movieDao.getMovieList(page)
        if (movies.isEmpty()) {
            val response = movieService.fetchDiscoverMovie(page)
            response.suspendOnSuccess {
                movies = data.results
                movies.forEach { it.page = page }
                movieDao.insertMovieList(movies)
                emit(movies)
            }.onError {
                error()
            }.onException { error() }
        } else {
            emit(movies)
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadKeywordList(id: Int) = flow<List<Keyword>> {
        val movie = movieDao.getMovie(id)
        var keywords = movie.keywords
        if (keywords.isNullOrEmpty()) {
            val response = movieService.fetchKeywords(id)
            response.suspendOnSuccess {
                keywords = data.keywords
                movie.keywords = keywords
                emit(keywords ?: listOf())
                movieDao.updateMovie(movie)
            }
        } else {
            emit(keywords ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadVideoList(id: Int) = flow<List<Video>> {
        val movie = movieDao.getMovie(id)
        var videos = movie.videos
        if (videos.isNullOrEmpty()) {
            movieService.fetchVideos(id)
                .suspendOnSuccess {
                    videos = data.results
                    movie.videos = videos
                    movieDao.updateMovie(movie)
                    emit(videos ?: listOf())
                }
        } else {
            emit(videos ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadCastList(id: Int) = flow<List<Cast>> {
        val movie = movieDao.getMovie(id)
        var casts = movie.casts
        if (casts.isNullOrEmpty()) {
            movieService.fetchCasts(id)
                .suspendOnSuccess {
                    casts = data.cast
                    movie.casts = casts
                    movieDao.updateMovie(movie)
                    emit(casts ?: listOf())
                }
        } else {
            emit(casts ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadReviewsList(id: Int) = flow<List<Review>> {
        val movie = movieDao.getMovie(id)
        var reviews = movie.reviews
        if (reviews.isNullOrEmpty()) {
            movieService.fetchReviews(id)
                .suspendOnSuccess {
                    reviews = data.results
                    movie.reviews = reviews
                    movieDao.updateMovie(movie)
                    emit(reviews ?: listOf())
                }
        } else {
            emit(reviews ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadMovieById(id: Int) = flow {
        val movie = movieDao.getMovie(id)
        emit(movie)
    }.flowOn(Dispatchers.IO)
}