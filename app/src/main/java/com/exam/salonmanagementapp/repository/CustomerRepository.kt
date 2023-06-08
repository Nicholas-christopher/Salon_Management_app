package com.exam.salonmanagementapp.repository

import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerRepository() {

    suspend fun register(customer: Customer): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            var result: Result<Boolean> = Result.Loading
            val db = Firebase.firestore
            db.collection(DataConstant.TABLE_CUSTOMER)
                .document(customer.id)
                .set(customer)
                .addOnSuccessListener {
                    result = Result.Success(true)
                }.addOnFailureListener {
                    result = Result.Error(Exception("Database opertaion failed"))
                }
                .await()
            result
        }
    }
}