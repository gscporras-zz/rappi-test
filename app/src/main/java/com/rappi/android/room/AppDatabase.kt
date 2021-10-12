package com.rappi.android.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rappi.android.models.entities.Movie
import com.rappi.android.models.entities.Person
import com.rappi.android.models.entities.Popular
import com.rappi.android.models.entities.TopRated
import com.rappi.android.room.converters.*

@Database(
    entities = [(Movie::class), (Popular::class), (TopRated::class), (Person::class)],
    version = 3, exportSchema = false
)
@TypeConverters(
    value = [
        (StringListConverter::class),
        (IntegerListConverter::class),
        (KeywordListConverter::class),
        (VideoListConverter::class),
        (ReviewListConverter::class),
        (CastListConverter::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun popularDao(): PopularDao
    abstract fun topRatedDao(): TopRatedDao
}