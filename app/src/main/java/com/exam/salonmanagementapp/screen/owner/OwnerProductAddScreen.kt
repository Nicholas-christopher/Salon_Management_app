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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Save
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
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.viewmodel.OwnerProductAddViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Composable
fun OwnerProductAddScreen(
    navController: NavController,
    ownerProductAddVM: OwnerProductAddViewModel
) {
    val context = LocalContext.current

    var useImageUri by rememberSaveable { mutableStateOf(false) }
    val painter: Painter = painterResource(id = R.drawable.profile)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        ownerProductAddVM.imageUri = it
        useImageUri = true
    }

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
                        Icon(Icons.Filled.Close, "back")
                    }
                },
                actions = {
                    // lock icon
                    IconButton(onClick = {
                        ownerProductAddVM.addProduct()
                    }) {
                        Icon(imageVector = Icons.Outlined.Save, contentDescription = "save")
                    }
                }
            )
        },
        content = { padding->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = if (useImageUri) rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = ownerProductAddVM.imageUri)
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
                CustomTextField(
                    value = ownerProductAddVM.productName,
                    onValueChange = { ownerProductAddVM.productName = it },
                    label = "Product Name",
                    leadingIconImageVector = Icons.Default.Description,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CustomTextField(
                    value = ownerProductAddVM.quantity,
                    onValueChange = {  ownerProductAddVM.quantity = it },
                    label = "Product Quantity",
                    leadingIconImageVector = Icons.Default.Description,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
            }
            when (ownerProductAddVM.addProductResult) {
                "LOADING" -> CircularProgressIndicator()
                "SUCCESS" -> navController.popBackStack()
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}


@Composable
@Preview(showBackground = true)
fun OwnerProductAddScreenPreview() {
    OwnerProductAddScreen(
        navController = rememberNavController(),
        ownerProductAddVM = viewModel()
    )
}