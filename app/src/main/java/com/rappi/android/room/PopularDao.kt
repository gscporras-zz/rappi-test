package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Movie
import com.rappi.android.models.entities.Popular

@Dao
interface PopularDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movies: List<Popular>)

    @Update
    suspend fun updateMovie(movie: Popular)

    @Query("SELECT * FROM Popular WHERE id = :id_")
    suspend fun getMovie(id_: Int): Popular

    @Query("SELECT * FROM Popular WHERE page = :page_")
    suspend fun getMovieList(page_: Int): List<Popular>?
}