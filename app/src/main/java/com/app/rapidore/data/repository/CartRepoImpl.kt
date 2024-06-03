package com.app.rapidore.data.repository

import androidx.room.withTransaction
import com.app.rapidore.common.Resource
import com.app.rapidore.data.db.ProductDatabase
import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.toCartModel
import com.app.rapidore.data.db.model.toProductModel
import com.app.rapidore.data.remote.ProductsApi
import com.app.rapidore.data.remote.dto.toDbModel
import com.app.rapidore.domain.model.CartModel
import com.app.rapidore.domain.repository.CartRepo
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepoImpl  @Inject constructor(
    private val api: ProductsApi,
    private val db: ProductDatabase
) : CartRepo {
    private val dao = db.productDao

    override suspend fun getAllCarts(): Flow<Resource<CartModel>> {
        return flow {
            try {
                val details = api.getCarts()
                db.withTransaction {
                    dao.deleteProduct()
                    val details = details.mapIndexed { index, item ->
                        item.toDbModel(details[index].id?:0)
                    }
                    dao.insertAllCarts(details)
                }
                delay(1000)
                val list=dao.fetchCartUser(1).first()
                emit(Resource.Success(list.toCartModel()))

            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    }
}