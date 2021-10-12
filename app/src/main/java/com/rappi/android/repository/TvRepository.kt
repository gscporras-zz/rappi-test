package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.models.Cast
import com.rappi.android.models.Video
import com.rappi.android.network.service.TvService
import com.rappi.android.room.TvDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class TvRepository constructor(
    private val tvService: TvService,
    private val tvDao: TvDao
) {

    init {
        Timber.d("Injection TvRepository")
    }

    @WorkerThread
    fun loadTvs(page: Int, success: () -> Unit, error: () -> Unit) = flow {
        var tvs = tvDao.getTvList(page)
        if (tvs.isEmpty()) {
            val response = tvService.fetchTv(page)
            response.suspendOnSuccess {
                tvs = data.results
                tvs.forEach { it.page = page }
                tvDao.insertTvList(tvs)
                emit(tvs)
            }.onError {
                error()
            }.onException { error() }
        } else {
            emit(tvs)
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadTvVideoList(id: Int) = flow<List<Video>> {
        val tv = tvDao.getTv(id)
        var videos = tv.videos
        if (videos.isNullOrEmpty()) {
            tvService.fetchTvVideos(id)
                .suspendOnSuccess {
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
    fun loadTvCastList(id: Int) = flow<List<Cast>> {
        val tv = tvDao.getTv(id)
        var casts = tv.casts
        if (casts.isNullOrEmpty()) {
            tvService.fetchTvCasts(id)
                .suspendOnSuccess {
                    casts = data.cast
                    tv.casts = casts
                    tvDao.updateTv(tv)
                    emit(casts ?: listOf())
                }
        } else {
            emit(casts ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadTvById(id: Int) = flow {
        val tv = tvDao.getTv(id)
        emit(tv)
    }.flowOn(Dispatchers.IO)
}