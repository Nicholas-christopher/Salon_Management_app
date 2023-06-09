package com.exam.salonmanagementapp.screen.owner;

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.viewmodel.OwnerLandingViewModel
import com.exam.salonmanagementapp.viewmodel.OwnerProductViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun  OwnerProductScreen(
    navController: NavController,
    ownerProductVM: OwnerProductViewModel
) {
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
                    // lock icon
                    IconButton(onClick = {
                        navController.navigate(route = Screen.OwnerProductAdd.route)
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "add")
                    }
                }
            )
        },
        content = { padding->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                when (ownerProductVM.productsResult) {
                    "" -> ownerProductVM.getProducts()
                    "LOADING" -> CircularProgressIndicator()
                    "SUCCESS" -> CustomProducts(navController, ownerProductVM.products)
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun OwnerProductScreenPreview() {
    OwnerProductScreen(
        navController = rememberNavController(),
        ownerProductVM = viewModel()
    )
}