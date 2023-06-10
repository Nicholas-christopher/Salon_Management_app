package com.exam.salonmanagementapp.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OwnerProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var product by mutableStateOf(Product())
    var productResult by mutableStateOf("")
    var useImageUri by mutableStateOf(false)

    var quantity by mutableStateOf("")

    var validateQuantity by  mutableStateOf(true)
        private set

    var validated by mutableStateOf(true)
        private set
    var saveProductResult by mutableStateOf("")

    fun validateData() {
        validateQuantity = quantity.isNotBlank() && quantity.isDigitsOnly()

        validated = validateQuantity
    }

    fun getProduct(productId: String) {
        viewModelScope.launch {
            productResult = "LOADING"
            val result = try {
                productRepository.getProduct(productId)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    productResult = "SUCCESS"
                    product = result.data
                    useImageUri = true
                }
                else -> {
                    productResult = "FAILED"
                }
            }
        }
    }

    fun addProduct() {
        viewModelScope.launch {
            saveProductResult = "LOADING"
            validateData()
            if (validated) {
                val product = Product(product.id, product.productName, product.productImage, product.quantity + quantity.toInt())
                val result = try {
                    productRepository.saveProduct(product)
                } catch(e: Exception) {
                    Result.Error(Exception("Network request failed"))
                }
                when (result) {
                    is Result.Success -> {
                        saveProductResult = "SUCCESS"
                    }
                    else -> {
                        saveProductResult = "FAILED"
                    }
                }
            }
        }
    }

    fun useProduct() {
        viewModelScope.launch {
            saveProductResult = "LOADING"
            validateData()
            if (validated) {
                if (product.quantity > quantity.toInt()) {
                    val product = Product(product.id, product.productName, product.productImage, product.quantity - quantity.toInt())
                    val result = try {
                        productRepository.saveProduct(product)
                    } catch(e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }
                    when (result) {
                        is Result.Success -> {
                            saveProductResult = "SUCCESS"
                        }
                        else -> {
                            saveProductResult = "FAILED"
                        }
                    }
                }
                else {
                    saveProductResult = "FAILED"
                }
            }
            else {
                saveProductResult = "FAILED"
            }
        }
    }

    fun deleteProduct() {
        viewModelScope.launch {
            saveProductResult = "LOADING"
            val result = try {
                productRepository.deleteProduct(product)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    saveProductResult = "SUCCESS"
                }
                else -> {
                    saveProductResult = "FAILED"
                }
            }
        }
    }
}