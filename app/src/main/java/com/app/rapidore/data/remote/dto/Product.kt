package com.app.rapidore.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId")
    val productId: Int? = 0,
    @SerializedName("quantity")
    val quantity: Int? = 0
)