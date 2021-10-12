package com.rappi.android.network.service

import com.rappi.android.models.network.KeywordListResponse
import com.rappi.android.models.network.ReviewListResponse
import com.rappi.android.models.network.VideoListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvService {
    /**
     * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-keywords)
     *
     * Get the keywords that have been added to a TV show.
     *
     * @param [id] Specify the id of tv keywords.
     *
     * @return [VideoListResponse] response
     */
    @GET("tv/{tv_id}/keywords")
    suspend fun fetchKeywords(@Path("tv_id") id: Long): ApiResponse<KeywordListResponse>

    /**
     * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-videos)
     *
     * Get the videos that have been added to a TV show.
     *
     * @param [id] Specify the id of tv id.
     *
     * @return [VideoListResponse] response
     */
    @GET("tv/{tv_id}/videos")
    suspend fun fetchVideos(@Path("tv_id") id: Long): ApiResponse<VideoListResponse>

    /**
     * [Tv Reviews](https://developers.themoviedb.org/3/tv/get-tv-reviews)
     *
     * Get the reviews for a TV show.
     *
     * @param [id] Specify the id of tv id.
     *
     * @return [ReviewListResponse] response
     */
    @GET("tv/{tv_id}/reviews")
    suspend fun fetchReviews(@Path("tv_id") id: Long): ApiResponse<ReviewListResponse>
}