package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnerCustomerHistoryViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var appointments by mutableStateOf(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    fun getCustomerAppointmentHistory(customerId: String) {
        viewModelScope.launch {
            appointmentsResult = "LOADING"
            val result = try {
                appointmentRepository.getCustomerAppointmentHistory(customerId)
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