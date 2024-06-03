package com.app.rapidore.data.repository

import androidx.room.withTransaction
import com.app.rapidore.common.Resource

import com.app.rapidore.data.db.ProductDatabase
import com.app.rapidore.data.db.model.ProductDBModel
import com.app.rapidore.data.remote.ProductsApi
import com.app.rapidore.data.remote.dto.AddToCartRequest
import com.app.rapidore.data.remote.dto.Product
import com.app.rapidore.data.remote.dto.toDbModel
import com.app.rapidore.domain.model.ProductModel
import com.app.rapidore.domain.repository.ProductRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val api: ProductsApi,
    private val db: ProductDatabase
) : ProductRepo {

    private val dao = db.productDao
    override suspend fun getProducts(): Flow<Resource<MutableList<ProductDBModel>>> {

        return flow {
            try {
                val details = api.getProducts()
                db.withTransaction {
                    dao.deleteProduct()
                    val details = details.mapIndexed { index, item ->
                        item.toDbModel(index + 1)
                    }
                    dao.insertAllProducts(details)
                }
                delay(1000)
                emit(Resource.Success(dao.fetchProducts().first()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }


        /*  networkBoundResource(
            query = {
                Log.d("GetProductsDB", "getProducts: ${"loading......"}")
                dao.fetchProducts()
            }, fetch = {
                delay(1000)
                Log.d("GetProducts", "getProducts: ${"loading......"}")
                api.getProducts()
            }, saveFetchResult = {
                db.withTransaction {
                    dao.deleteProduct()
                    val details = it.mapIndexed { index, item ->
                        item.toDbModel(index + 1)
                    }
                    dao.insertAllProducts(details)
                }
            }
        )*/


    }

    override suspend fun getProduct(id: Int): Flow<Resource<ProductDBModel>> {
        return flow {
            try {
                val details = api.getProduct(id)
                emit(Resource.Success(details.toDbModel(id)))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }


    override suspend fun cartUpdated(product: ProductModel?): Flow<Resource<Boolean>> {
        return flow {
            val response = api.insertCart(
                AddToCartRequest(
                    "2020-02-03",
                    listOf(Product(product?.id, 1)),
                    userId = 1
                )
            )

            if (response.isSuccessful && response.code() == 200) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error(response.message()))
            }
        }

    }
}





