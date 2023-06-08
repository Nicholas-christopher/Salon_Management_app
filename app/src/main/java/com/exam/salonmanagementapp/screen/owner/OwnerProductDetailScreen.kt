package com.exam.salonmanagementapp.screen.owner

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@Composable
fun OwnerProductDetailScreen (
    navController: NavController,
    productId: String
) {
    val context = LocalContext.current

    OwnerBackground (
        navController = navController
    ) {
        var productName by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf("") }
        var imageUri by remember { mutableStateOf("") }
        var useImageUri by remember { mutableStateOf(false) }
        val painter: Painter = painterResource(id = R.drawable.profile)
        var quantityUsed by remember { mutableStateOf("0") }
        var validateQuantityUsed by remember { mutableStateOf(true) }

        val db = Firebase.firestore
        db.collection(DataConstant.TABLE_PRODUCT)
            .whereEqualTo("id", productId)
            .get()
            .addOnSuccessListener { documents ->
                System.out.println("documents.size() => " + documents.size())
                if (!documents.isEmpty) {
                    val product = documents.first().toObject<Product>()
                    productName = product.productName
                    quantity = product.quantity.toString()
                    imageUri = product.productImage
                    useImageUri = true
                }
            }

        fun validateData(quantityUsed: Int ): Boolean {
            validateQuantityUsed = quantity.toInt() >= quantityUsed
            return validateQuantityUsed
        }

        fun saveProduct( quantityUsed: Int ) {
            if (validateData( quantityUsed )){
                val db = Firebase.firestore
                val product = Product(productId, productName, imageUri, quantity.toInt() - quantityUsed)

                db.collection(DataConstant.TABLE_PRODUCT)
                    .document(product.id)
                    .set(product)
                    .addOnSuccessListener {
                        navController.popBackStack()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Add product failed!", Toast.LENGTH_SHORT).show()
                    }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    .size(100.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Product Name : $productName",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )

            Text(
                text = "Product Quantity :  ${quantity.toString()}",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )

            CustomTextField(
                value = quantityUsed,
                onValueChange = { quantityUsed = it },
                label = "Product Used",
                showError = !validateQuantityUsed,
                errorMessage = context.resources.getString(R.string.validate_product_quantityUsed),
                leadingIconImageVector = Icons.Default.Description,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
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
                            saveProduct(quantityUsed.toInt())
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(
                            text = "Use Product"
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun OwnerProductDetailScreenPreview() {
    OwnerProductDetailScreen(
        navController = rememberNavController(),
        productId = "1"
    )
}