package com.exam.salonmanagementapp.data

data class Appointment(
    val customerId: String = "",
    val appointmentDate: String = "",
    val appointmentTime: String = "",
    val service: String = "",
    val description: String = "",
    val status: String = "NEW",
    val amount: Float = 0f,
)
