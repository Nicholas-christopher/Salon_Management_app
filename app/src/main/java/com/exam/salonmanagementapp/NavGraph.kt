package com.exam.salonmanagementapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exam.salonmanagementapp.screen.LoginScreen
import com.exam.salonmanagementapp.screen.RegistrationScreen
import com.exam.salonmanagementapp.screen.customer.CustomerLandingScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.Registration.route
        ) {
            RegistrationScreen(navController = navController)
        }
        composable(
            route = Screen.CustomerLanding.route
        ) {
            CustomerLandingScreen(navController = navController)
        }
    }
}