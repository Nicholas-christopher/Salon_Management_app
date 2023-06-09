package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnerCustomerViewModel @Inject constructor(
    private val customerRepository : CustomerRepository
) : ViewModel() {

    var customers by mutableStateOf<List<Customer>>(listOf<Customer>())
    var customersResult by mutableStateOf("")

    fun getCustomerList() {
        viewModelScope.launch {
            customersResult = "LOADING"
            val result = try {
                customerRepository.getCustomerList()
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    customersResult = "SUCCESS"
                    customers = result.data
                }
                else -> {
                    customersResult = "FAILED"
                }
            }
        }
    }
}