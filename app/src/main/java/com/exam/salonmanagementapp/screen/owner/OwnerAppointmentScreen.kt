package com.exam.salonmanagementapp.screen.owner

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.OwnerBackground

@Composable
fun  OwnerAppointmentScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {

    }
}


@Composable
@Preview(showBackground = true)
fun OwnerAppointmentScreenPreview() {
    OwnerAppointmentScreen(
        navController = rememberNavController()
    )
}