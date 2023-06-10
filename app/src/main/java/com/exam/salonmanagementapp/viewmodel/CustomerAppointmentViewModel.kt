package com.exam.salonmanagementapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class CustomerAppointmentViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    var appointmentDate by mutableStateOf("")
    var time by mutableStateOf("")
    var service by mutableStateOf("")
    var description by mutableStateOf("")

    var validateAppointmentDate by mutableStateOf(true)
    var validateTime by mutableStateOf(true)
    var validateService by mutableStateOf(true)

    var validated by mutableStateOf(false)
        private set
    var saveResult by mutableStateOf("")

    fun validateData() {

        validateAppointmentDate = appointmentDate.isNotBlank()
        validateTime = time.isNotBlank()
        validateService = service.isNotBlank()

        validated = validateAppointmentDate && validateTime && validateService
    }

    fun saveAppointment(customerId: String) {
        viewModelScope.launch {
            saveResult = "LOADING"
            validateData()
            if (validated) {
                val appointment = Appointment(UUID.randomUUID().toString(), customerId, SimpleDateFormat("d/M/yyyy").parse(appointmentDate), time, service, description)
                val result = try {
                    appointmentRepository.makeAppointment(appointment)
                } catch(e: Exception) {
                    Result.Error(Exception("Network request failed"))
                }
                when (result) {
                    is Result.Success -> {
                        saveResult = "SUCCESS"
                    }
                    else -> {
                        saveResult = "FAILED"
                    }
                }
            }
            else {
                saveResult = "FAILED"
            }
        }
    }
}