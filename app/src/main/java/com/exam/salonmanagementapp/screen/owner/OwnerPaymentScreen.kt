package com.exam.salonmanagementapp.screen.owner;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.viewmodel.OwnerAppointmentDetailViewModel
import com.exam.salonmanagementapp.viewmodel.OwnerPaymentViewModel
import java.text.SimpleDateFormat

@Composable
fun OwnerPaymentScreen(
    navController: NavController,
    ownerAppointmentDetailVM: OwnerAppointmentDetailViewModel,
    ownerPaymentVM: OwnerPaymentViewModel,
    appointmentId: String
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

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
                    IconButton(onClick = {
                        ownerPaymentVM.makePayment(
                            ownerAppointmentDetailVM.appointment.appointmentId,
                            ownerAppointmentDetailVM.appointment.customerId,
                            ownerAppointmentDetailVM.appointment)
                    }) {
                        Icon(Icons.Filled.Save, "payment")
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

                    CustomTextField(
                        value = ownerPaymentVM.amount,
                        onValueChange = { ownerPaymentVM.amount = it },
                        label = "Total (RM)",
                        showError = !ownerPaymentVM.validateAmount,
                        errorMessage = context.resources.getString(R.string.validate_amount_error),
                        leadingIconImageVector = Icons.Default.Money,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus( ) }
                        )
                    )
                }
                when (ownerPaymentVM.appointmentResult) {
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
fun OwnerPaymentScreenPreview() {
    OwnerPaymentScreen(
        navController = rememberNavController(),
        ownerAppointmentDetailVM = viewModel(),
        ownerPaymentVM = viewModel(),
        appointmentId = "1"
    )
}