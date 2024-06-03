package com.app.rapidore.presentation.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.app.rapidore.common.Resource
import com.app.rapidore.domain.model.ProductModel
import com.app.rapidore.domain.usecase.ProductUseCase
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ItemDescriptionViewModel @Inject constructor(private val useCase: ProductUseCase) : ViewModel(){
    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val loading: LiveData<Boolean>
        get() = _loading


    private val _id: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val id: LiveData<Int>
        get() = _id

    private val _error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val error: LiveData<String>
        get() = _error

    private val _product: MutableLiveData<ProductModel> by lazy {
        MutableLiveData<ProductModel>()
    }
    val product: LiveData<ProductModel>
        get() = _product

     val addedToCart=LiveEvent<Boolean?>()

    val rating= _product.map {
        it.rating.rate.toFloat()
    }.distinctUntilChanged()

    fun getProducts(productId:Int) {
        useCase.invokeProduct(productId).onEach {
            when (it) {
                is Resource.Success -> {
                    _product.value = it.data
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

    fun setArgs(productId: Int) {
       _id.postValue(productId)
    }

    fun addToCart() {
      useCase.invokeAddToCart(_product.value).onEach {
          when (it) {
              is Resource.Success -> {
                 addedToCart.postValue(it.data)
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