package com.app.rapidore.domain.model


import com.app.rapidore.data.remote.dto.Product

class CartModel (
    val id: Int? = 0,
    val products: MutableList<Product> = mutableListOf(),
    val userId:Int? = 0
)