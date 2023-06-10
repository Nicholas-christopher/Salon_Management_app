package com.exam.salonmanagementapp.data

import java.util.*

data class Appointment(
    val appointmentId: String = "",
    val customerId: String = "",
    val appointmentDate: Date = Date(),
    val appointmentTime: String = "",
    val service: String = "",
    val description: String = "",
    val status: String = "NEW",
    val amount: Float = 0f,
)
