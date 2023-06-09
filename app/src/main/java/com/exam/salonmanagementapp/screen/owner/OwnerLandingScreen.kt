package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.*
import com.exam.salonmanagementapp.viewmodel.OwnerLandingViewModel
import kotlinx.coroutines.launch


@Composable
fun OwnerLandingScreen(
    navController: NavController,
    ownerLandingVM: OwnerLandingViewModel
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { OwnerTopBar(navController = navController) },
        content = { padding->
            OwnerContent(navController = navController, scrollState = scrollState ) {
                Text(text = "Today Appointment(s)")

                when (ownerLandingVM.appointmentsResult) {
                    "" -> ownerLandingVM.getTodayAppointments()
                    "LOADING" -> CircularProgressIndicator()
                    "SUCCESS" -> CustomAppointments(navController, ownerLandingVM.appointments)
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )

}


@Composable
@Preview(showBackground = true)
fun OwnerLandingScreenPreview() {
OwnerLandingScreen(
    navController = rememberNavController(),
    ownerLandingVM = viewModel()
)
}