package com.app.rapidore.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.app.rapidore.data.remote.dto.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "tbl_cart")
@TypeConverters(CartDBModel.Converters::class)
class CartDBModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    val userId:Int?=0,
    val products: MutableList<Product> = mutableListOf(),
) {
    class Converters {
        @TypeConverter
        fun decode(data: String?): List<Product> {
            val type: Type = object : TypeToken<List<Product>>() {}.type
            return Gson().fromJson(data, type)
        }
        @TypeConverter
        fun encode(someObjects: List<Product>): String = Gson().toJson(someObjects)
    }
}