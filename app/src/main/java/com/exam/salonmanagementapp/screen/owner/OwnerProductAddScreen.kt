package com.exam.salonmanagementapp.screen.owner

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@Composable
fun OwnerProductAddScreen(
    navController: NavController,

) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var productName by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf(0) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var useImageUri by rememberSaveable { mutableStateOf(false) }
    val painter: Painter = painterResource(id = R.drawable.profile)
    var showProgressBar by rememberSaveable {mutableStateOf(false)}
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        useImageUri = true
    }

    fun validateData(productName: String, quantity: Int, useImageUri: Boolean): Boolean {
        return true
    }

    fun saveProduct(productName: String, quantity: Int, useImageUri: Boolean, imageUri: Uri?) {
        if (validateData(productName, quantity, useImageUri)){
            showProgressBar = true
            var storage = FirebaseStorage.getInstance().reference.child(DataConstant.STORAGE_PRODUCT_IMAGE).child(System.currentTimeMillis().toString())
            val db = Firebase.firestore

            imageUri?.let {
                storage.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storage.downloadUrl.addOnSuccessListener { uri ->
                            val product = Product(productName, uri.toString(), quantity)
                            showProgressBar = false

                            db.collection(DataConstant.TABLE_PRODUCT)
                                .add(product)
                                .addOnSuccessListener {
                                    navController.popBackStack()
                                }.addOnFailureListener {
                                    Toast.makeText(context, "Add product failed!", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        showProgressBar = false
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    OwnerBackground (
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = if (useImageUri) rememberImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = imageUri)
                            .build()
                    ) else painter,
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )

            }
        }
        CustomTextField(
            value = productName,
            onValueChange = { productName = it },
            label = "Product Name",
            leadingIconImageVector = Icons.Default.Description,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        CustomTextField(
            value = quantity.toString(),
            onValueChange = { quantity = it.toInt() },
            label = "Product Quantity",
            leadingIconImageVector = Icons.Default.Description,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        if(showProgressBar){
            CircularProgressIndicator()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
                Button(
                    onClick = {
                        saveProduct(productName, quantity, useImageUri, imageUri)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Add Product"
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun OwnerProductAddScreenPreview() {
    OwnerProductAddScreen(
        navController = rememberNavController()
    )
}