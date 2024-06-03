package com.app.rapidore.data.db.model

import androidx.room.Embedded
import androidx.room.Relation


data class CartWithProductDetails(
@Embedded val cart: CartDBModel,
@Relation(
    parentColumn = "id",
    entityColumn = "id"
)
val products: List<ProductDBModel>
)
