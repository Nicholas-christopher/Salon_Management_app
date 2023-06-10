package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
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
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.viewmodel.OwnerAppointmentViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun  OwnerAppointmentScreen(
    navController: NavController,
    ownerAppointmentVM: OwnerAppointmentViewModel
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OwnerTopBar(
                navController = navController,
                title = "Appointment(s)",
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
                    when (ownerAppointmentVM.appointmentsResult) {
                        "" -> ownerAppointmentVM.getAppointments()
                        "SUCCESS" -> CustomAppointments(navController, ownerAppointmentVM.appointments, Screen.OwnerAppointmentDetail)
                    }
                }
                when (ownerAppointmentVM.appointmentsResult) {
                    "LOADING" -> CircularProgressIndicator()
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun OwnerAppointmentScreenPreview() {
    OwnerAppointmentScreen(
        navController = rememberNavController(),
        ownerAppointmentVM = viewModel()
    )
}