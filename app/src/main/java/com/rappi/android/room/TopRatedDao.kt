package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Movie
import com.rappi.android.models.entities.TopRated

@Dao
interface TopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movies: List<TopRated>)

    @Update
    suspend fun updateMovie(movie: TopRated)

    @Query("SELECT * FROM TopRated WHERE id = :id_")
    suspend fun getMovie(id_: Int): TopRated

    @Query("SELECT * FROM TopRated WHERE page = :page_")
    suspend fun getMovieList(page_: Int): List<TopRated>?
}