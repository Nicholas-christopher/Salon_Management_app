package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Payments
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.viewmodel.OwnerCustomerDetailViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Composable
fun  OwnerCustomerDetailScreen(
    navController: NavController,
    ownerCustomerDetailVM: OwnerCustomerDetailViewModel,
    customerId: String,
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Customer Detail",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, "menu")
                    }
                }
            )
        },
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .clickable {
                        navController.navigate(route = Screen.OwnerCustomerHistory.passId(customerId))
                    }

            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = "history"
                    )

                    Text(
                        modifier = Modifier.padding(start = 24.dp),
                        text = "History",
                    )
                }
            }
        },
        content = { padding ->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column(){
                    when (ownerCustomerDetailVM.customerResult) {
                        "" -> ownerCustomerDetailVM.getCustomer(customerId)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.DarkGray)
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(color = Color.White, text = "Customer Information")
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(color = Color.White, text = "Customer Name : ${ownerCustomerDetailVM.customer.name}")
                        Text(color = Color.White, text = "Email : ${ownerCustomerDetailVM.customer.email}")
                        Text(color = Color.White, text = "Phone Number : ${ownerCustomerDetailVM.customer.phone}")
                    }
                    when (ownerCustomerDetailVM.customerResult) {
                        "LOADING" -> CircularProgressIndicator()
                    }

                    Column() {
                        when (ownerCustomerDetailVM.appointmentsResult) {
                            "" -> ownerCustomerDetailVM.getCustomerAppointments(customerId)
                            "SUCCESS" -> CustomAppointments(navController, ownerCustomerDetailVM.appointments, Screen.OwnerAppointmentDetail)
                        }
                    }
                    when (ownerCustomerDetailVM.appointmentsResult) {
                        "LOADING" -> CircularProgressIndicator()
                    }
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun OwnerCustomerDetailScreenPreview() {
    OwnerCustomerDetailScreen(
        navController = rememberNavController(),
        ownerCustomerDetailVM = viewModel(),
        customerId = "1",
    )
}

