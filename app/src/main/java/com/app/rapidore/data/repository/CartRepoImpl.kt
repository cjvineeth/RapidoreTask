package com.app.rapidore.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.app.rapidore.common.Resource
import com.app.rapidore.data.db.ProductDatabase
import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.ProductDBModel
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
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CartRepoImpl  @Inject constructor(
    private val api: ProductsApi,
    private val db: ProductDatabase
) : CartRepo {
    private val dao = db.productDao

    override suspend fun getAllCarts(): Flow<Resource<List<ProductDBModel>?>> {
        return flow {
            try {
                val details = api.getCarts()
                db.withTransaction {
                    val details = details.mapIndexed { index, item ->
                        item.toDbModel(details[index].id ?: 0)
                    }
                    dao.insertAllCarts(details)
                }

                val list = dao.fetchCartUser(1).first()
                val productList = mutableListOf<ProductDBModel>()


              list.toCartModel().products.forEach { product ->
                  productList.add( dao.fetchProduct(product.productId).first())
                }


                emit(Resource.Success(productList))

            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}