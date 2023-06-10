package com.exam.salonmanagementapp.repository

import android.net.Uri
import android.widget.Toast
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class ProductRepository {

    val db = Firebase.firestore

    suspend fun addProduct(product: Product,  imageUri: Uri?): Result<Boolean> {
        lateinit var result:Result<Boolean>
        var uploadResult:Boolean = false

        var storage = FirebaseStorage.getInstance().reference.child(DataConstant.STORAGE_PRODUCT_IMAGE).child(System.currentTimeMillis().toString())
        storage.putFile(imageUri!!).addOnCompleteListener { task ->
            uploadResult = true
        }.addOnFailureListener {
            result = Result.Error(Exception("Upload image failed"))
        }.await()

        var downloadResult:Boolean = false
        var remoteUri:String = ""
        if (uploadResult) {
            storage.downloadUrl.addOnSuccessListener { uri ->
                downloadResult = true
                remoteUri = uri.toString()
            }.addOnFailureListener {
                result = Result.Error(Exception("Download URL failed"))
            }.await()
        }

        if (downloadResult) {
            val productWithUri = Product(product.id, product.productName, remoteUri, product.quantity)
            db.collection(DataConstant.TABLE_PRODUCT)
                .document(productWithUri.id)
                .set(productWithUri)
                .addOnSuccessListener {
                    result = Result.Success(true)
                }.addOnFailureListener {
                    result = Result.Error(Exception("Database opertaion failed"))
                }.await()
        }
        return result
    }

    suspend fun getProducts(): Result<List<Product>> {
        lateinit var result:Result<List<Product>>
        db.collection(DataConstant.TABLE_PRODUCT)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Product>())
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

    suspend fun getProduct(productId: String): Result<Product> {
        lateinit var result:Result<Product>
        db.collection(DataConstant.TABLE_PRODUCT)
            .whereEqualTo("id", productId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.first().toObject<Product>())
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

    suspend fun saveProduct(product: Product): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_PRODUCT)
            .document(product.id)
            .set(product)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }.await()
        return result
    }

    suspend fun deleteProduct(product: Product): Result<Boolean> {
        lateinit var result:Result<Boolean>
        db.collection(DataConstant.TABLE_PRODUCT)
            .document(product.id)
            .delete()
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Error(Exception("Database opertaion failed"))
            }.await()
        return result
    }

    suspend fun getLowStockProducts(): Result<List<Product>> {
        lateinit var result:Result<List<Product>>
        db.collection(DataConstant.TABLE_PRODUCT)
            .whereLessThanOrEqualTo("quantity", 5)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    result = Result.Success(documents.toObjects<Product>())
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