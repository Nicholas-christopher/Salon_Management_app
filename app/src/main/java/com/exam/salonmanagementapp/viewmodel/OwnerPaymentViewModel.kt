package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Payment
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.PaymentRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OwnerPaymentViewModel@Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    var amount by mutableStateOf("")
    var validateAmount by mutableStateOf(true)
    var savePayment by mutableStateOf(Payment())
        private set
    var saveAppointment by mutableStateOf(Appointment())
        private set

    var validated by mutableStateOf(false)
        private set

    var paymentResult by mutableStateOf("")
    var appointmentResult by mutableStateOf("")

    fun validateData() {
        val amountRegex = "^\\d+(\\.\\d+)$".toRegex()
        validateAmount = amountRegex.matches(amount)
        validated = validateAmount
    }

    fun mapToPayment(appointmentId: String, customerId: String) {
        if (savePayment.customerId != customerId || savePayment.appointmentId != appointmentId || savePayment.amount != amount.toFloat()) {
            savePayment = Payment("", customerId, appointmentId, amount.toFloat())
        }
    }

    fun makePayment(appointmentId: String, customerId: String, appointment: Appointment) {
        viewModelScope.launch {
            appointmentResult = "LOADING"
            paymentResult = "LOADING"
            validateData()
            if (validated) {
                mapToPayment(appointmentId, customerId)
                val result = try {
                    paymentRepository.makePayment(savePayment)
                } catch(e: Exception) {
                    Result.Error(Exception("Network request failed"))
                }
                System.out.println("makePayment() => result => $result")
                when (result) {
                    is Result.Success -> {
                        completeAppointment(appointment)
                        paymentResult = "SUCCESS"
                    }
                    else -> {
                        paymentResult = "FAILED"
                    }
                }
            }
            else {
                appointmentResult = ""
                paymentResult = ""
            }
        }
    }

    fun mapToAppointment(appointment: Appointment) {
        if (saveAppointment.status != "COMPLETED") {
            saveAppointment = Appointment(
                appointment.appointmentId,
                appointment.customerId,
                appointment.appointmentDate,
                appointment.appointmentTime,
                appointment.service,
                appointment.description,
                "COMPLETED",
                amount.toFloat()
            )
        }
    }

    fun completeAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentResult = "LOADING"
            mapToAppointment(appointment)
            val result = try {
                appointmentRepository.completeAppointment(saveAppointment)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            System.out.println("completeAppointment() => result => $result")
            when (result) {
                is Result.Success -> {
                    appointmentResult = "SUCCESS"
                }
                else -> {
                    appointmentResult = "FAILED"
                }
            }
        }
    }
}