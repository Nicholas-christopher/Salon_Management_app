package com.exam.salonmanagementapp.screen.customer

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomAppointments
import com.exam.salonmanagementapp.component.CustomerBottomBar
import com.exam.salonmanagementapp.component.CustomerTopBar
import com.exam.salonmanagementapp.component.OwnerContent
import com.exam.salonmanagementapp.viewmodel.OwnerCustomerHistoryViewModel

@Composable
fun CustomerHistoryScreen(
    navController: NavController,
    ownerCustomerHistoryVM: OwnerCustomerHistoryViewModel
) {
    val context = LocalContext.current
    val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
    val customerId = sharedPreference.getString("customerId", null)

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomerTopBar(
                navController = navController,
                title = "Appointment History",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.Close, "back")
                    }
                },
                actions = {
                }
            )
        },
        content = { padding ->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column() {
                    Text(text = "Upcoming Appointment(s)")
                    Spacer(modifier = Modifier.height(12.dp))
                    when (ownerCustomerHistoryVM.appointmentsResult) {
                        "" -> ownerCustomerHistoryVM.getCustomerAppointmentHistory(customerId!!)
                        "SUCCESS" -> CustomAppointments(navController, ownerCustomerHistoryVM.appointments, Screen.CustomerAppointmentDetail)
                    }
                }
                when (ownerCustomerHistoryVM.appointmentsResult) {
                    "LOADING" -> CircularProgressIndicator()
                }
            }
        },
        bottomBar = { CustomerBottomBar(navController =  navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun CustomerHistoryScreenPreview() {
    CustomerHistoryScreen(
        navController = rememberNavController(),
        ownerCustomerHistoryVM = viewModel()
    )
}
