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

    var validated by mutableStateOf(false)
        private set

    var paymentResult by mutableStateOf("")
    var appointmentResult by mutableStateOf("")

    fun validateData() {
        val amountRegex = "^\\d+(\\.\\d+)$".toRegex()
        validateAmount = amountRegex.matches(amount)
        validated = validateAmount
    }

    fun makePayment(appointmentId: String, customerId: String, appointment: Appointment) {
        viewModelScope.launch {
            appointmentResult = "LOADING"
            paymentResult = "LOADING"
            validateData()
            if (validated) {
                val payment = Payment(UUID.randomUUID().toString(), customerId, appointmentId, amount.toFloat())
                val result = try {
                    paymentRepository.makePayment(payment)
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

    fun completeAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentResult = "LOADING"
            val updatedAppointment = Appointment(
                appointment.appointmentId,
                appointment.customerId,
                appointment.appointmentDate,
                appointment.appointmentTime,
                appointment.service,
                appointment.description,
                "COMPLETED",
                amount.toFloat()
            )
            val result = try {
                appointmentRepository.completeAppointment(updatedAppointment)
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