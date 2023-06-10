package com.exam.salonmanagementapp.data

data class Payment(
    val id: String = "",
    val customerId: String = "",
    val appointmentId: String = "",
    val amount: Float = 0f,
)
