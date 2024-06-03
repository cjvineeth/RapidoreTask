package com.app.rapidore.data.remote.dto

import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.ProductDBModel

fun ProductItem.toDbModel(int: Int): ProductDBModel {
    return ProductDBModel(
        id = int,
        title = title,
        url = image,
        rating = rating.rate,
        price = price,
        ratingCount = rating.count,
        description = description,
        category = category
    )
}

    fun CartResponseItem.toDbModel(int: Int): CartDBModel {
        return CartDBModel(
            id = int,
            products =  products,
            userId = userId
        )
}


