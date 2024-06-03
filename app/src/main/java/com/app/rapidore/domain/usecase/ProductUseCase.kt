package com.app.rapidore.domain.usecase


import com.app.rapidore.common.Resource
import com.app.rapidore.data.db.model.toProductModel
import com.app.rapidore.data.repository.ProductRepoImpl
import com.app.rapidore.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val planetaryRepo: ProductRepoImpl) {

    fun invokeProductDetails(): Flow<Resource<MutableList<ProductModel>?>> = flow {
        try {
            emit(Resource.Loading())
            planetaryRepo.getProducts().collect { resource ->
                emit(Resource.Success(resource.data?.map {
                    it.toProductModel()
                }?.toMutableList()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }

    fun invokeProduct(id:Int)
            : Flow<Resource<ProductModel?>> = flow {
        try {
            emit(Resource.Loading())
            planetaryRepo.getProduct(id).collect { resource ->
                emit(Resource.Success(resource.data?.toProductModel()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }

    fun invokeAddToCart(product: ProductModel?): Flow<Resource<Boolean?>> = flow {
        try {
            emit(Resource.Loading())
            planetaryRepo.cartUpdated(product).collect { resource ->
                emit(Resource.Success(resource.data))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }

}