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

class OwnerAppointmentDetailViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    var appointment by mutableStateOf(Appointment())
    var appointmentResult by mutableStateOf("")

    var customer by mutableStateOf(Customer())
    var customerResult by mutableStateOf("")

    var deleteAppointmentResult by mutableStateOf("")

    fun getAppointment(appointmentId: String) {
        viewModelScope.launch {
            appointmentResult = "LOADING"
            val result = try {
                appointmentRepository.getAppointment(appointmentId)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    appointmentResult = "SUCCESS"
                    appointment = result.data
                }
                else -> {
                    appointmentResult = "FAILED"
                }
            }
        }
    }

    fun getCustomer(customerId: String) {
        viewModelScope.launch {
            appointmentResult = "COMPLETED"
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

    fun deleteAppointment(appointmentId: String) {
        viewModelScope.launch {
            appointmentResult = "COMPLETED"
            deleteAppointmentResult = "LOADING"
            val result = try {
                appointmentRepository.deleteAppointment(appointmentId)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success -> {
                    deleteAppointmentResult = "SUCCESS"
                }
                else -> {
                    deleteAppointmentResult = "FAILED"
                }
            }
        }
    }
}