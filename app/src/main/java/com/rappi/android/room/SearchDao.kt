package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Search

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movies: List<Search>)

    @Update
    suspend fun updateMovie(movie: Search)

    @Query("SELECT * FROM Search WHERE id = :id_")
    suspend fun getMovie(id_: Int): Search

    @Query("SELECT * FROM Search WHERE page = :page_ AND title OR name LIKE '%' || :query_ || '%'")
    suspend fun getMovieList(query_: String?, page_: Int): List<Search>
}