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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OwnerLandingViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var appointments by mutableStateOf<List<Appointment>>(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    fun getTodayAppointments() {
        viewModelScope.launch {
            val todayCalendar = Calendar.getInstance()
            appointmentsResult = "LOADING"
            val result = try {
                appointmentRepository.getTodayAppointments(SimpleDateFormat("dd/MM/yyyy").format(todayCalendar.time))
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