package com.exam.salonmanagementapp.repository

import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Payment
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class AppointmentRepository {

    val db = Firebase.firestore

    suspend fun makeAppointment(appointment: Appointment): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .document(appointment.appointmentId)
            .set(appointment)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getAppointments(): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        var today : Calendar = Calendar.getInstance()
        today.set(Calendar.HOUR, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereGreaterThanOrEqualTo("appointmentDate", today.time)
            .orderBy("appointmentDate", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Appointment>())
                }
                else {
                    result = Result.Error(Exception("No result return"))
                }
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
                if (!documents.isEmpty) {
                    result = Result.Success(documents.first().toObject<Appointment>())
                }
                else {
                    result = Result.Error(Exception("No result return"))
                }
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getUpcomingAppointments(): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        var today : Calendar = Calendar.getInstance()
        today.set(Calendar.HOUR, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereGreaterThanOrEqualTo("appointmentDate", today.time)
            .orderBy("appointmentDate", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Appointment>())
                }
                else {
                    result = Result.Error(Exception("No result return"))
                }
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getCustomerAppointments(customerId: String): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        var today : Calendar = Calendar.getInstance()
        today.set(Calendar.HOUR, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        db.collection(DataConstant.TABLE_APPOINTMENT).where(
                Filter.and(
                    Filter.equalTo("customerId", customerId),
                    Filter.greaterThanOrEqualTo("appointmentDate", today.time)
                )
            )
            .orderBy("appointmentDate", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Appointment>())
                }
                else {
                    result = Result.Error(Exception("No result return"))
                }
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getCustomerAppointmentHistory(customerId: String): Result<List<Appointment>> {
        lateinit var result:Result<List<Appointment>>
        var today : Calendar = Calendar.getInstance()
        today.set(Calendar.HOUR, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        db.collection(DataConstant.TABLE_APPOINTMENT).where(
            Filter.and(
                Filter.equalTo("customerId", customerId),
                Filter.lessThan("appointmentDate", today.time)
            )
        )
            .orderBy("appointmentDate", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Appointment>())
                }
                else {
                    result = Result.Error(Exception("No result return"))
                }
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun completeAppointment(appointment: Appointment): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .document(appointment.appointmentId)
            .set(appointment)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun deleteAppointment(appointmentId: String): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_APPOINTMENT)
            .document(appointmentId)
            .delete()
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }
}