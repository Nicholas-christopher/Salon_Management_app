package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.viewmodel.OwnerCustomerViewModel

@Composable
fun OwnerCustomerScreen(
    navController: NavController,
    ownerCustomerVM: OwnerCustomerViewModel
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Customer(s)",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                actions = {}
            )
        },
        content = { padding ->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column() {
                    when (ownerCustomerVM.customersResult) {
                        "" -> ownerCustomerVM.getCustomers()
                        "SUCCESS" -> CustomCustomerDetails(navController, ownerCustomerVM.customers)
                    }
                }
                when (ownerCustomerVM.customersResult) {
                    "LOADING" -> CircularProgressIndicator()
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun OwnerCustomerScreenPreview() {
    OwnerCustomerScreen(
        navController = rememberNavController(),
        ownerCustomerVM = viewModel()
    )
}