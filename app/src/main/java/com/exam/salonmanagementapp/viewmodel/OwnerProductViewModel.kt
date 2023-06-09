package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnerProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(listOf<Product>())
    var productsResult by mutableStateOf("")

    fun getProducts() {
        viewModelScope.launch {
            productsResult = "LOADING"
            val result = try {
                productRepository.getProducts()
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    productsResult = "SUCCESS"
                    products = result.data
                }
                else -> {
                    productsResult = "FAILED"
                }
            }
        }
    }
}