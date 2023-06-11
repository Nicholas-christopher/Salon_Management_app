package com.exam.salonmanagementapp.viewmodel

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OwnerProductAddViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var productName by mutableStateOf("")
    var quantity by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)
    var saveProduct by mutableStateOf(Product())

    var validateProductName by mutableStateOf(true)
        private set
    var validateQuantity by  mutableStateOf(true)
        private set
    var validateImageUri by mutableStateOf(true)
        private set
    var validated by mutableStateOf(false)
        private set
    var addProductResult by mutableStateOf("")

    fun validateData() {
        val quantityRegex = "^[\\d]+".toRegex()

        validateProductName = productName.isNotBlank()
        validateQuantity = quantityRegex.matches(quantity)
        validateImageUri = imageUri != null

        validated = validateProductName && validateQuantity && validateImageUri
    }

    fun mapToProduct() {
        if (saveProduct.productName != productName || saveProduct.quantity != quantity.toInt()) {
            saveProduct = Product("", productName, "", quantity.toInt())
        }
    }

    fun addProduct() {
        viewModelScope.launch {
            addProductResult = "LOADING"
            validateData()
            if (validated) {
                mapToProduct()
                val result = try {
                    productRepository.addProduct(saveProduct, imageUri)
                } catch(e: Exception) {
                    Result.Error(Exception("Network request failed"))
                }
                when (result) {
                    is Result.Success -> {
                        addProductResult = "SUCCESS"
                    }
                    else -> {
                        addProductResult = "FAILED"
                    }
                }
            }
        }
    }
}