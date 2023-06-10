package com.exam.salonmanagementapp.screen.customer

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.viewmodel.CustomerLandingViewModel
import com.exam.salonmanagementapp.viewmodel.OwnerLandingViewModel

@Composable
fun CustomerLandingScreen(
    navController: NavController,
    customerLandingVM: CustomerLandingViewModel
) {
    val context = LocalContext.current
    val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
    val customerId = sharedPreference.getString("customerId", null)
    val customerName = sharedPreference.getString("customerName", "")

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CustomerTopBar(navController = navController, customerName = customerName!!) },
        content = { padding ->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Column() {
                    Text(text = "Upcoming Appointment(s)")
                    Spacer(modifier = Modifier.height(12.dp))
                    when (customerLandingVM.appointmentsResult) {
                        "" -> customerLandingVM.getTodayAppointments(customerId!!)
                        "SUCCESS" -> CustomAppointments(navController, customerLandingVM.appointments)
                    }
                }
                when (customerLandingVM.appointmentsResult) {
                    "LOADING" -> CircularProgressIndicator()
                }
            }
        },
        bottomBar = { CustomerBottomBar(navController =  navController) }
    )
}

@Composable
@Preview(showBackground = true)
fun CustomerLandingScreenPreview() {
    CustomerLandingScreen(
        navController = rememberNavController(),
        customerLandingVM = viewModel()
    )
}