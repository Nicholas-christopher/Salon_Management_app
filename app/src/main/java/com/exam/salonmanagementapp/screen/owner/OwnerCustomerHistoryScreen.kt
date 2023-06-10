package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.viewmodel.OwnerCustomerHistoryViewModel

@Composable
fun OwnerCustomerHistoryScreen(
    navController: NavController,
    ownerCustomerHistoryVM : OwnerCustomerHistoryViewModel,
    customerId: String
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Customer History",
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
                    when (ownerCustomerHistoryVM.appointmentsResult) {
                        "" -> ownerCustomerHistoryVM.getCustomerAppointmentHistory(customerId)
                        "SUCCESS" -> CustomAppointments(navController, ownerCustomerHistoryVM.appointments, Screen.OwnerAppointmentDetail)
                    }
                }
                when (ownerCustomerHistoryVM.appointmentsResult) {
                    "LOADING" -> CircularProgressIndicator()
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun OwnerCustomerHistoryScreenPreview() {
    OwnerCustomerHistoryScreen(
        navController = rememberNavController(),
        ownerCustomerHistoryVM = viewModel(),
        customerId = "1"
    )
}