package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payments
import androidx.compose.runtime.Composable
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
import com.exam.salonmanagementapp.viewmodel.OwnerAppointmentDetailViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Composable
fun OwnerAppointmentDetailScreen(
    navController: NavController,
    ownerAppointmentDetailVM: OwnerAppointmentDetailViewModel,
    appointmentId: String
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Appointment Detail",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    if (ownerAppointmentDetailVM.appointment.status == "NEW") {
                        IconButton(onClick = {
                            ownerAppointmentDetailVM.deleteAppointment(appointmentId)
                        }) {
                            Icon(Icons.Filled.Delete, "delete")
                        }
                        IconButton(onClick = {
                            navController.navigate(Screen.OwnerPayment.passId(ownerAppointmentDetailVM.appointment.appointmentId))
                        }) {
                            Icon(Icons.Filled.Payments, "payment")
                        }
                    }
                }
            )
        },
        content = { padding ->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column(){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.DarkGray)
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (ownerAppointmentDetailVM.appointmentResult) {
                            "" -> ownerAppointmentDetailVM.getAppointment(appointmentId)
                            "LOADING" -> CircularProgressIndicator()
                            "SUCCESS" ->  ownerAppointmentDetailVM.getCustomer( ownerAppointmentDetailVM.appointment.customerId)
                        }
                        Text(color = Color.White, text = "Appointment Information")
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(color = Color.White, text = "Date : " + SimpleDateFormat("dd/MM/yyyy").format(ownerAppointmentDetailVM.appointment.appointmentDate) )
                        Text(color = Color.White, text = "Time : ${ownerAppointmentDetailVM.appointment.appointmentTime}")
                        Text(color = Color.White, text = "Service : ${ownerAppointmentDetailVM.appointment.service}")
                        Text(color = Color.White, text = "Description : ${ownerAppointmentDetailVM.appointment.description}")
                        Text(color = Color.White, text = "Status : ${ownerAppointmentDetailVM.appointment.status}")
                        if (ownerAppointmentDetailVM.appointment.status == "COMPLETED") {
                            Text(color = Color.White, text = "Total (RM) : " + DecimalFormat("#.00").format(ownerAppointmentDetailVM.appointment.amount))
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (ownerAppointmentDetailVM.customerResult) {
                            "LOADING" -> CircularProgressIndicator()
                        }
                        Text(text = "Customer Information")
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Customer Name : ${ownerAppointmentDetailVM.customer.name}")
                        Text(text = "Email : ${ownerAppointmentDetailVM.customer.email}")
                        Text(text = "PhoneNumber : ${ownerAppointmentDetailVM.customer.phone}")
                    }
                }
                when (ownerAppointmentDetailVM.deleteAppointmentResult) {
                    "LOADING" -> CircularProgressIndicator()
                    "SUCCESS" -> navController.navigate(Screen.OwnerLanding.route)
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}


@Composable
@Preview(showBackground = true)
fun OwnerAppointmentDetailScreenPreview() {
    OwnerAppointmentDetailScreen(
        navController = rememberNavController(),
        ownerAppointmentDetailVM = viewModel(),
        appointmentId = "1"
    )
}