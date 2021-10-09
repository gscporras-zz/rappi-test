package com.rappi.android.data.network

import com.rappi.android.BuildConfig.DomainApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MovieApiClient {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)


    private val retrofit = Retrofit.Builder()
        .baseUrl(DomainApi)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient.build())

    val service: MovieFetchApi by lazy {
        retrofit.build().
        create(MovieFetchApi::class.java)
    }

}