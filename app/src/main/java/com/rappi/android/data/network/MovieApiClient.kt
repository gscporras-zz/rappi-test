package com.rappi.android.data.network

import com.rappi.android.BuildConfig.DomainApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MovieApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(DomainApi)
        .addConverterFactory(MoshiConverterFactory.create())
    val  service:MovieFetchApi by lazy {
        retrofit.build().
        create(MovieFetchApi::class.java)
    }

}