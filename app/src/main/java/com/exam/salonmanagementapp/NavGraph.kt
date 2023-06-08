package com.exam.salonmanagementapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.screen.LoginScreen
import com.exam.salonmanagementapp.screen.RegistrationScreen
import com.exam.salonmanagementapp.screen.customer.CustomerAppointmentScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryDetailScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryScreen
import com.exam.salonmanagementapp.screen.customer.CustomerLandingScreen
import com.exam.salonmanagementapp.screen.customer.CustomerProfileScreen
import com.exam.salonmanagementapp.screen.owner.OwnerAppointmentDetailScreen
import com.exam.salonmanagementapp.screen.owner.OwnerAppointmentScreen
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
import com.exam.salonmanagementapp.viewmodel.RegistrationViewModel

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
            val registrationVM: RegistrationViewModel = RegistrationViewModel(customerRepository = CustomerRepository())
            RegistrationScreen(navController = navController, registrationVM = registrationVM)
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
            arguments = listOf(navArgument(ARGUMENT_KEY_ID){
                type = NavType.StringType
            })
        ) {
            OwnerPaymentDetailScreen(navController = navController, paymentId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
        }
        composable(
            route = Screen.OwnerProduct.route
        ) {
            OwnerProductScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerProductDetail.route,
            arguments = listOf(navArgument(ARGUMENT_KEY_ID){
                type = NavType.StringType
            })
        ) {
            OwnerProductDetailScreen(navController = navController, productId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
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
            arguments = listOf(navArgument(ARGUMENT_KEY_ID){
                type = NavType.StringType
            })
        ) {
            OwnerCustomerDetailScreen(navController = navController, customerId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
        }
        composable(
            route = Screen.OwnerProfile.route
        ) {
            OwnerProfileScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerAppointment.route
        ) {
            OwnerAppointmentScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerAppointmentDetail.route,
            arguments = listOf(navArgument(ARGUMENT_KEY_ID){
                type = NavType.StringType
            })
        ) {
            OwnerAppointmentDetailScreen(navController = navController, appointmentId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
        }

    }
}