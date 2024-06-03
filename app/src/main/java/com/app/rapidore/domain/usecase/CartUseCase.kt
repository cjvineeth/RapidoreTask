package com.app.rapidore.domain.usecase

import com.app.rapidore.common.Resource
import com.app.rapidore.data.repository.CartRepoImpl
import com.app.rapidore.domain.model.CartModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartUseCase  @Inject constructor(private val planetaryRepo: CartRepoImpl) {

    fun cartDetails(): Flow<Resource<CartModel?>> = flow {
        try {
            emit(Resource.Loading())
            planetaryRepo.getAllCarts().collect { resource ->
                emit(Resource.Success(resource.data))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }
}