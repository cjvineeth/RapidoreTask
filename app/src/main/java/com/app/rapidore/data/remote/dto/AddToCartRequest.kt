package com.app.rapidore.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AddToCartRequest(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("products")
    val products: List<Product>? = listOf(),
    @SerializedName("userId")
    val userId: Int? = 0
)