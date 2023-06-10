package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnerCustomerDetailViewModel @Inject constructor(
    private val customerRepository : CustomerRepository,
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var customer by mutableStateOf(Customer())
    var customerResult by mutableStateOf("")

    var appointments by mutableStateOf(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    fun getCustomer(customerId: String) {
        viewModelScope.launch {
            customerResult = "LOADING"
            val result = try {
                customerRepository.getCustomer(customerId)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    customerResult = "SUCCESS"
                    customer = result.data
                }
                else -> {
                    customerResult = "FAILED"
                }
            }
        }
    }

    fun getCustomerAppointments(customerId: String) {
        viewModelScope.launch {
            appointmentsResult = "LOADING"
            val result = try {
                appointmentRepository.getCustomerAppointments(customerId)
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
}