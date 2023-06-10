package com.exam.salonmanagementapp.repository

import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Payment
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

class PaymentRepository {

    val db = Firebase.firestore

    suspend fun makePayment(payment: Payment): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_PAYMENT)
            .document(payment.id)
            .set(payment)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }
}