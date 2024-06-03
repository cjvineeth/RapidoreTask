package com.app.rapidore.data.db.model

import com.app.rapidore.data.remote.dto.Rating
import com.app.rapidore.domain.model.CartModel
import com.app.rapidore.domain.model.ProductModel


fun ProductDBModel.toProductModel(): ProductModel {
    return ProductModel(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = url,
        rating = Rating(rating, ratingCount)
    )
}

fun CartDBModel.toCartModel(): CartModel {
    return CartModel(
        id = id,
        products = products,
        userId = userId
    )
}
