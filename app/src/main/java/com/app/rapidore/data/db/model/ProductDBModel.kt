package com.app.rapidore.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ProductDBModel(
    @PrimaryKey
    var id: Int,
    var title: String,
    var url: String,
    var rating:String,
    var ratingCount:Int,
    var description:String,
    var category:String,
    var price:String
)