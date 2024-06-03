package com.app.rapidore.domain.repository

import com.app.rapidore.common.Resource
import com.app.rapidore.data.db.model.ProductDBModel
import com.app.rapidore.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepo {
     suspend fun getProducts(): Flow<Resource<MutableList<ProductDBModel>>>
     suspend fun getProduct(id: Int): Flow<Resource<ProductDBModel>>
     suspend fun   cartUpdated(product: ProductModel?): Flow<Resource<Boolean>>

}