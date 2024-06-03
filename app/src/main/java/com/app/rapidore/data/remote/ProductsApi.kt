package com.app.rapidore.data.remote

import com.app.rapidore.data.remote.dto.AddToCartRequest
import com.app.rapidore.data.remote.dto.CartResponseItem
import com.app.rapidore.data.remote.dto.ProductItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(
    ): MutableList<ProductItem>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): ProductItem

    @GET("carts")
    suspend fun getCarts(
    ): MutableList<CartResponseItem>

    @POST("carts")
    suspend fun insertCart(@Body cart: AddToCartRequest
    ): Response<Unit>
}