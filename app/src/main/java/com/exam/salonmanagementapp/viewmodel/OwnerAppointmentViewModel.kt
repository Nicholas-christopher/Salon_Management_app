package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnerAppointmentViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var appointments by mutableStateOf<List<Appointment>>(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    fun getAppointments() {
        viewModelScope.launch {
            appointmentsResult = "LOADING"
            val result = try {
                appointmentRepository.getAppointments()
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