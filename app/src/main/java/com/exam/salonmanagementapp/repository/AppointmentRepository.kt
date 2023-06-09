package com.exam.salonmanagementapp.repository

import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AppointmentRepository {

    val db = Firebase.firestore

    suspend fun getAppointments(): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .get()
            .addOnSuccessListener { documents ->
                result = Result.Success(documents.toObjects<Appointment>())
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getAppointment(appointmentId: String): Result<Appointment> {
        lateinit var result:Result<Appointment>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereEqualTo("appointmentId", appointmentId)
            .get()
            .addOnSuccessListener { documents ->
                result = Result.Success(documents.first().toObject<Appointment>())
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getTodayAppointments(appointmentDate: String): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereEqualTo("appointmentDate", appointmentDate)
            .get()
            .addOnSuccessListener { documents ->
                result = Result.Success(documents.toObjects<Appointment>())
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getCustomerAppointments(customerId: String): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereEqualTo("customerId", customerId)
            .get()
            .addOnSuccessListener { documents ->
                result = Result.Success(documents.toObjects<Appointment>())
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }
}