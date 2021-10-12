package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Tv

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tvs: List<Tv>)

    @Update
    suspend fun updateTv(tv: Tv)

    @Query("SELECT * FROM Tv WHERE id = :id_")
    suspend fun getTv(id_: Long): Tv?

    @Query("SELECT * FROM Tv WHERE page = :page_")
    suspend fun getTvList(page_: Int): List<Tv>
}