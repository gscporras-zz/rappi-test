package com.rappi.android.di

import android.content.Context
import coil.ImageLoader
import com.rappi.android.BuildConfig.*
import com.rappi.android.network.RequestInterceptor
import com.rappi.android.network.service.MovieService
import com.rappi.android.network.service.PeopleService
import com.rappi.android.network.service.TheDiscoverService
import com.rappi.android.network.service.TvService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okhHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okhHttpClient)
            .baseUrl(DomainApi)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTheDiscoverService(retrofit: Retrofit): TheDiscoverService {
        return retrofit.create(TheDiscoverService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvService(retrofit: Retrofit): TvService {
        return retrofit.create(TvService::class.java)
    }

    @Provides
    @Singleton
    fun providePeopleService(retrofit: Retrofit): PeopleService {
        return retrofit.create(PeopleService::class.java)
    }
}