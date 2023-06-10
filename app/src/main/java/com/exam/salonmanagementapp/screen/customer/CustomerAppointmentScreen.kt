package com.exam.salonmanagementapp.screen.customer

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.viewmodel.CustomerAppointmentViewModel
import java.util.*

@Composable
fun CustomerAppointmentScreen(
    navController: NavController,
    customerAppointmentVM: CustomerAppointmentViewModel
){
    val context = LocalContext.current
    val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
    val customerId = sharedPreference.getString("customerId", null)

    val todayCalendar = Calendar.getInstance()
    var isTimeExpanded by remember{ mutableStateOf(false) }
    var isServiceExpanded by remember{ mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomerTopBar(
                navController = navController,
                title = "Make Appointment",
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
                        customerAppointmentVM.saveAppointment(customerId!!)
                    }) {
                        Icon(imageVector = Icons.Outlined.Save, contentDescription = "save")
                    }
                }
            )
        },
        content = { padding->
            CustomerContent(navController = navController, scrollState = scrollState ) {
                Column() {
                    CustomDatePicker(
                        value = customerAppointmentVM.appointmentDate,
                        onValueChange = { customerAppointmentVM.appointmentDate = it },
                        label = "Appointment Date",
                        leadingIconImageVector = Icons.Default.EditCalendar,
                        curYear = todayCalendar.get(Calendar.YEAR),
                        curMonth = todayCalendar.get(Calendar.MONTH),
                        curDay = todayCalendar.get(Calendar.DAY_OF_MONTH),
                        showError = !customerAppointmentVM.validateAppointmentDate,
                        errorMessage = context.resources.getString(R.string.validate_appointment_date),
                    )

                    CustomDropDownMenu(
                        label = "Time",
                        services = listOf("10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00"),
                        isExpanded = isTimeExpanded,
                        onExpanded = { isTimeExpanded = it },
                        selectedItem = customerAppointmentVM.time,
                        onValueChange = { customerAppointmentVM.time = it },
                        leadingIconImageVector = Icons.Default.WatchLater,
                        showError = !customerAppointmentVM.validateTime,
                        errorMessage = context.resources.getString(R.string.validate_appointment_time),
                    )

                    CustomDropDownMenu(
                        label = "Services",
                        services = listOf("Hair Treatment","Spa","Perm","HairCut","Wash and Blow Dry"),
                        isExpanded = isServiceExpanded,
                        onExpanded = { isServiceExpanded = it },
                        selectedItem = customerAppointmentVM.service,
                        onValueChange = { customerAppointmentVM.service = it },
                        leadingIconImageVector = Icons.Default.ContentCut,
                        showError = !customerAppointmentVM.validateService,
                        errorMessage = context.resources.getString(R.string.validate_appointment_service),
                    )

                    CustomTextField(
                        value = customerAppointmentVM.description,
                        onValueChange = { customerAppointmentVM.description = it },
                        label = "Description",
                        leadingIconImageVector = Icons.Default.Description,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                }

            }
            when (customerAppointmentVM.saveResult) {
                "LOADING" -> CircularProgressIndicator()
                "SUCCESS" -> navController.popBackStack()
            }
        },
        bottomBar = { CustomerBottomBar(navController = navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun CustomerAppointmentScreenPreview() {
    CustomerAppointmentScreen(
        navController = rememberNavController(),
        customerAppointmentVM = viewModel()
    )
}



