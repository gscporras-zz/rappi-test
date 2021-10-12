package com.rappi.android.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rappi.android.models.Cast

open class CastListConverter {
    @TypeConverter
    fun fromString(value: String): List<Cast>? {
        val listType = object : TypeToken<List<Cast>>() {}.type
        return Gson().fromJson<List<Cast>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Cast>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}