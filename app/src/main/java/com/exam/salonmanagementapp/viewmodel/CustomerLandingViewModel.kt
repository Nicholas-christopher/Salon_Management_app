package com.exam.salonmanagementapp.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CustomerLandingViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var appointments by mutableStateOf<List<Appointment>>(listOf<Appointment>())
    var appointmentsResult by mutableStateOf("")

    fun getTodayAppointments(customerId: String) {
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