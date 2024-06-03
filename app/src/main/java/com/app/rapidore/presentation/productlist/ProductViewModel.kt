package com.app.rapidore.presentation.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.rapidore.common.Resource
import com.app.rapidore.domain.model.ProductModel
import com.app.rapidore.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val useCase: ProductUseCase) : ViewModel(){

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

    private val _apodList: MutableLiveData<MutableList<ProductModel>> by lazy {
        MutableLiveData<MutableList<ProductModel>>()
    }
    val apodList: LiveData<MutableList<ProductModel>>
        get() = _apodList


    fun getProducts() {
        useCase.invokeProductDetails().onEach {
            when (it) {
                is Resource.Success -> {
                    _apodList.value = it.data
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