package com.app.rapidore.data.db.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Converters that are reused across multiple tables.
 * https://developer.android.com/training/data-storage/room/referencing-data#type-converters
 */
class CommonConverters {

    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String {
        return Gson().toJson(intList)
    }
    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(stringList, type)
    }
}