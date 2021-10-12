package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.models.Keyword
import com.rappi.android.models.Review
import com.rappi.android.models.Video
import com.rappi.android.network.service.TvService
import com.rappi.android.room.TvDao
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class TvRepository constructor(
    private val tvService: TvService,
    private val tvDao: TvDao
) : Repository {

    init {
        Timber.d("Injection TvRepository")
    }

    @WorkerThread
    fun loadKeywordList(id: Long) = flow<List<Keyword>> {
        val tv = tvDao.getTv(id) ?: return@flow
        var keywords = tv.keywords
        if (keywords.isNullOrEmpty()) {
            val response = tvService.fetchKeywords(id)
            response.suspendOnSuccess {
                keywords = data.keywords
                tv.keywords = keywords
                tvDao.updateTv(tv)
                emit(keywords ?: listOf())
            }
        } else {
            emit(keywords ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadVideoList(id: Long) = flow<List<Video>> {
        val tv = tvDao.getTv(id) ?: return@flow
        var videos = tv.videos
        if (videos.isNullOrEmpty()) {
            val response = tvService.fetchVideos(id)
            response.suspendOnSuccess {
                videos = data.results
                tv.videos = videos
                tvDao.updateTv(tv)
                emit(videos ?: listOf())
            }
        } else {
            emit(videos ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadReviewsList(id: Long) = flow<List<Review>> {
        val tv = tvDao.getTv(id) ?: return@flow
        var reviews = tv.reviews
        if (reviews.isNullOrEmpty()) {
            val response = tvService.fetchReviews(id)
            response.suspendOnSuccess {
                reviews = data.results
                tv.reviews = reviews
                tvDao.updateTv(tv)
                emit(reviews ?: listOf())
            }
        } else {
            emit(reviews ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadTvById(id: Long) = flow {
        val tv = tvDao.getTv(id)
        emit(tv)
    }.flowOn(Dispatchers.IO)
}