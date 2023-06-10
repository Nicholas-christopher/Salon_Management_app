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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Save
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.viewmodel.OwnerProductDetailViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@Composable
fun OwnerProductDetailScreen (
    navController: NavController,
    ownerProductDetailVM: OwnerProductDetailViewModel,
    productId: String
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Product(s)",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        ownerProductDetailVM.addProduct()
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "add")
                    }
                    IconButton(onClick = {
                        ownerProductDetailVM.useProduct()
                    }) {
                        Icon(imageVector = Icons.Outlined.Remove, contentDescription = "use")
                    }
                    IconButton(onClick = {
                        ownerProductDetailVM.deleteProduct()
                    }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
                    }
                }
            )
        },
        content = { padding->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = if (ownerProductDetailVM.useImageUri) rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = ownerProductDetailVM.product.productImage)
                                    .build()
                            ) else painterResource(id = R.drawable.profile),
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "Product Name : ${ownerProductDetailVM.product.productName}",
                            modifier = Modifier
                                .padding(vertical = 10.dp)

                        )

                        Text(
                            text = "Product Quantity :  ${ownerProductDetailVM.product.quantity.toString()}",
                            modifier = Modifier
                                .padding(vertical = 10.dp)

                        )
                    }

                    CustomTextField(
                        value = ownerProductDetailVM.quantity,
                        onValueChange = { ownerProductDetailVM.quantity = it },
                        label = "Product Used",
                        showError = !ownerProductDetailVM.validateQuantity,
                        errorMessage = context.resources.getString(R.string.validate_product_quantityUsed),
                        leadingIconImageVector = Icons.Default.Description,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                }
                when (ownerProductDetailVM.productResult) {
                    "" -> ownerProductDetailVM.getProduct(productId)
                    "LOADING" -> CircularProgressIndicator()
                }
                when (ownerProductDetailVM.saveProductResult) {
                    "LOADING" -> CircularProgressIndicator()
                    "SUCCESS" -> navController.popBackStack()
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}


@Composable
@Preview(showBackground = true)
fun OwnerProductDetailScreenPreview() {
    OwnerProductDetailScreen(
        navController = rememberNavController(),
        ownerProductDetailVM = viewModel(),
        productId = "1"
    )
}