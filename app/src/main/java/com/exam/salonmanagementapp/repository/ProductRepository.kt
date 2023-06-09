package com.exam.salonmanagementapp.repository

import android.net.Uri
import android.widget.Toast
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class ProductRepository {

    val db = Firebase.firestore

    suspend fun addProduct(product: Product,  imageUri: Uri?): Result<Boolean> {
        lateinit var result:Result<Boolean>
        var storage = FirebaseStorage.getInstance().reference.child(DataConstant.STORAGE_PRODUCT_IMAGE).child(System.currentTimeMillis().toString())
        imageUri?.let {
            storage.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storage.downloadUrl.addOnSuccessListener { uri ->
                        val productWithUri = Product(product.id, product.productName, uri.toString(), product.quantity)
                        db.collection(DataConstant.TABLE_PRODUCT)
                            .document(productWithUri.id)
                            .set(productWithUri)
                            .addOnSuccessListener {
                                result = Result.Success(true)
                            }.addOnFailureListener {
                                result = Result.Error(Exception("Database opertaion failed"))
                            }
                    }.addOnFailureListener {
                        result = Result.Error(Exception("Download URL failed"))
                    }
                } else {
                    result = Result.Error(Exception("Upload image failed"))
                }
            }
        }
        return result
    }

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

    suspend fun getProducts(): Result<List<Product>> {
        lateinit var result:Result<List<Product>>
        db.collection(DataConstant.TABLE_PRODUCT)
            .get()
            .addOnSuccessListener { documents ->
                result = Result.Success(documents.toObjects<Product>())
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }
            .await()
        return result
    }
}