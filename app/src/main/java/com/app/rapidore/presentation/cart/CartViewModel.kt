package com.app.rapidore.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.rapidore.common.Resource
import com.app.rapidore.domain.model.CartModel
import com.app.rapidore.domain.model.ProductModel
import com.app.rapidore.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val useCase: CartUseCase) : ViewModel(){

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val error: LiveData<String>
        get() = _error

    private val _cartList: MutableLiveData<MutableList<ProductModel>> by lazy {
        MutableLiveData<MutableList<ProductModel>>()
    }
    val cartList: LiveData<MutableList<ProductModel>>
        get() = _cartList


    fun getCarts() {
        useCase.cartDetails().onEach {
            when (it) {
                is Resource.Success -> {
                    _cartList.value = it.data
                    _loading.value = false
                }
                is Resource.Error -> {
                    _error.value = it.message
                    _loading.value = false
                }
                is Resource.Loading -> {
                    _loading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }
}