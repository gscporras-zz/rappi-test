package com.rappi.android.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rappi.android.models.entities.*
import com.rappi.android.room.converters.*

@Database(
    entities = [(Tv::class), (Movie::class), (Popular::class), (TopRated::class), (Search::class)],
    version = 3, exportSchema = false
)
@TypeConverters(
    value = [
        (StringListConverter::class),
        (IntegerListConverter::class),
        (VideoListConverter::class),
        (CastListConverter::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tvDao(): TvDao
    abstract fun movieDao(): MovieDao
    abstract fun popularDao(): PopularDao
    abstract fun topRatedDao(): TopRatedDao
    abstract fun searchDao(): SearchDao
}