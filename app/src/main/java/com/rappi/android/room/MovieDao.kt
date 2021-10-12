package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movies: List<Movie>)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :id_")
    suspend fun getMovie(id_: Int): Movie

    @Query("SELECT * FROM Movie WHERE page = :page_")
    suspend fun getMovieList(page_: Int): List<Movie>
}