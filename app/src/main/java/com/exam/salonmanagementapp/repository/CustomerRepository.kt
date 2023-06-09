package com.exam.salonmanagementapp.repository

import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerRepository() {

    val db = Firebase.firestore

    suspend fun register(customer: Customer): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_CUSTOMER)
            .document(customer.id)
            .set(customer)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }

    suspend fun getCustomer(customerId: String): Result<Customer> {
        lateinit var result:Result<Customer>
        db.collection(DataConstant.TABLE_CUSTOMER)
            .orderBy("name")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success( documents.first().toObject<Customer>())
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

    suspend fun getCustomerList(): Result<List<Customer>> {
        lateinit var result:Result<List<Customer>>
        db.collection(DataConstant.TABLE_CUSTOMER)
            .orderBy("name")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success( documents.toObjects<Customer>())
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

    suspend fun getCustomerList(customerName: String): Result<List<Customer>> {
        lateinit var result:Result<List<Customer>>
        db.collection(DataConstant.TABLE_CUSTOMER)
            .orderBy("name")
            .whereEqualTo("name", customerName)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success( documents.toObjects<Customer>())
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
}