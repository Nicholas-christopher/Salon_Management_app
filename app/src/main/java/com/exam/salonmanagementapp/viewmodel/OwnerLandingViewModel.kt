package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OwnerLandingViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    var appointments by mutableStateOf<List<Appointment>>(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    var products by mutableStateOf<List<Product>>(listOf<Product>())
    var productssResult by mutableStateOf("")

    fun getTodayAppointments() {
        viewModelScope.launch {
            appointmentsResult = "LOADING"
            val result = try {
                appointmentRepository.getUpcomingAppointments()
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    appointmentsResult = "SUCCESS"
                    appointments = result.data
                }
                else -> {
                    appointmentsResult = "FAILED"
                }
            }
        }
    }

    fun getLowStockProducts() {
        viewModelScope.launch {
            val todayCalendar = Calendar.getInstance()
            productssResult = "LOADING"
            val result = try {
                productRepository.getLowStockProducts()
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    productssResult = "SUCCESS"
                    products = result.data
                }
                else -> {
                    productssResult = "FAILED"
                }
            }
        }
    }
}