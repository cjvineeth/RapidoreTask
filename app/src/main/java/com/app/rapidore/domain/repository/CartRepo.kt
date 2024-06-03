package com.app.rapidore.domain.repository

import com.app.rapidore.common.Resource
import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.ProductDBModel
import com.app.rapidore.domain.model.CartModel
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun getAllCarts(): Flow<Resource<CartModel>>
}