package com.exam.salonmanagementapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exam.salonmanagementapp.screen.LoginScreen
import com.exam.salonmanagementapp.screen.RegistrationScreen
import com.exam.salonmanagementapp.screen.customer.CustomerAppointmentScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryDetailScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryScreen
import com.exam.salonmanagementapp.screen.customer.CustomerLandingScreen
import com.exam.salonmanagementapp.screen.customer.CustomerProfileScreen
import com.exam.salonmanagementapp.screen.owner.OwnerCustomerDetailScreen
import com.exam.salonmanagementapp.screen.owner.OwnerCustomerPaymentScreen
import com.exam.salonmanagementapp.screen.owner.OwnerCustomerScreen
import com.exam.salonmanagementapp.screen.owner.OwnerLandingScreen
import com.exam.salonmanagementapp.screen.owner.OwnerPaymentDetailScreen
import com.exam.salonmanagementapp.screen.owner.OwnerPaymentScreen
import com.exam.salonmanagementapp.screen.owner.OwnerProductAddScreen
import com.exam.salonmanagementapp.screen.owner.OwnerProductDetailScreen
import com.exam.salonmanagementapp.screen.owner.OwnerProductScreen
import com.exam.salonmanagementapp.screen.owner.OwnerProfileScreen

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
        composable(
            route = Screen.CustomerAppointment.route
        ) {
            CustomerAppointmentScreen(navController = navController)
        }
        composable(
            route = Screen.CustomerHistory.route
        ) {
            CustomerHistoryScreen(navController = navController)
        }
        composable(
            route = Screen.CustomerHistoryDetail.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            CustomerHistoryDetailScreen(navController = navController)
        }
        composable(
            route = Screen.CustomerProfile.route
        ) {
            CustomerProfileScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerLanding.route
        ) {
            OwnerLandingScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerPayment.route
        ) {
            OwnerPaymentScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerPaymentDetail.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            OwnerPaymentDetailScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerProduct.route
        ) {
            OwnerProductScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerProductDetail.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            OwnerProductDetailScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerProductAdd.route
        ) {
            OwnerProductAddScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerCustomer.route
        ) {
            OwnerCustomerScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerCustomerPayment.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            OwnerCustomerPaymentScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerCustomerDetail.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            OwnerCustomerDetailScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerProfile.route
        ) {
            OwnerProfileScreen(navController = navController)
        }
    }
}