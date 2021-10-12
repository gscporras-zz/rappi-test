package com.rappi.android.room

import androidx.room.*
import com.rappi.android.models.entities.Person

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: List<Person>)

    @Update
    suspend fun updatePerson(person: Person)

    @Query("SELECT * FROM people WHERE id = :id_")
    suspend fun getPerson(id_: Long): Person

    @Query("SELECT * FROM People WHERE page = :page_")
    suspend fun getPeople(page_: Int): List<Person>
}