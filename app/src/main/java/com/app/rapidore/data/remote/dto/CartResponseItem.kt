package com.app.rapidore.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CartResponseItem(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("products")
    val products: MutableList<Product> = mutableListOf(),
    @SerializedName("userId")
    val userId: Int? = 0,
    @SerializedName("__v")
    val v: Int? = 0
)