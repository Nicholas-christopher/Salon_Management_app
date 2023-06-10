package com.exam.salonmanagementapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.PaymentRepository
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.screen.LoginScreen
import com.exam.salonmanagementapp.screen.RegistrationScreen
import com.exam.salonmanagementapp.screen.customer.CustomerAppointmentScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryDetailScreen
import com.exam.salonmanagementapp.screen.customer.CustomerHistoryScreen
import com.exam.salonmanagementapp.screen.customer.CustomerLandingScreen
import com.exam.salonmanagementapp.screen.customer.CustomerProfileScreen
import com.exam.salonmanagementapp.screen.owner.*
import com.exam.salonmanagementapp.viewmodel.*

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
            val loginVM: LoginViewModel = LoginViewModel(customerRepository = CustomerRepository())
            LoginScreen(navController = navController, loginVM = loginVM)
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
            val customerLandingVM: CustomerLandingViewModel = CustomerLandingViewModel(appointmentRepository = AppointmentRepository())
            CustomerLandingScreen(navController = navController, customerLandingVM = customerLandingVM)
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
            val ownerLandingVM: OwnerLandingViewModel = OwnerLandingViewModel(appointmentRepository = AppointmentRepository())
            OwnerLandingScreen(navController = navController, ownerLandingVM = ownerLandingVM)
        }
        composable(
            route = Screen.OwnerPayment.route,
            arguments = listOf(navArgument(ARGUMENT_KEY_ID) {
                type = NavType.StringType
            })
        ) {
            val ownerAppointmentDetailVM: OwnerAppointmentDetailViewModel = OwnerAppointmentDetailViewModel(appointmentRepository = AppointmentRepository(), customerRepository = CustomerRepository())
            val ownerPaymentVM: OwnerPaymentViewModel = OwnerPaymentViewModel(appointmentRepository = AppointmentRepository(), paymentRepository = PaymentRepository())
            OwnerPaymentScreen(navController = navController, ownerAppointmentDetailVM= ownerAppointmentDetailVM, ownerPaymentVM = ownerPaymentVM, appointmentId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
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
            val ownerProductVM: OwnerProductViewModel = OwnerProductViewModel(productRepository = ProductRepository())
            OwnerProductScreen(navController = navController, ownerProductVM = ownerProductVM)
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
            val ownerProductAddVM: OwnerProductAddViewModel = OwnerProductAddViewModel(productRepository = ProductRepository())
            OwnerProductAddScreen(navController = navController, ownerProductAddVM = ownerProductAddVM)
        }
        composable(
            route = Screen.OwnerCustomer.route
        ) {
            val ownerCustomerVM: OwnerCustomerViewModel = OwnerCustomerViewModel(customerRepository = CustomerRepository())
            OwnerCustomerScreen(navController = navController, ownerCustomerVM = ownerCustomerVM)
        }
        composable(
            route = Screen.OwnerCustomerHistory.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) {
            val ownerCustomerHistoryVM: OwnerCustomerHistoryViewModel = OwnerCustomerHistoryViewModel(appointmentRepository = AppointmentRepository())
            OwnerCustomerHistoryScreen(navController = navController, customerId = it.arguments?.getString(ARGUMENT_KEY_ID).toString(), ownerCustomerHistoryVM = ownerCustomerHistoryVM)
        }
        composable(
            route = Screen.OwnerCustomerDetail.route,
            arguments = listOf(navArgument(ARGUMENT_KEY_ID){
                type = NavType.StringType
            })
        ) {
            val ownerCustomerDetailVM: OwnerCustomerDetailViewModel = OwnerCustomerDetailViewModel(customerRepository = CustomerRepository(), appointmentRepository = AppointmentRepository())
            OwnerCustomerDetailScreen(navController = navController, ownerCustomerDetailVM = ownerCustomerDetailVM, customerId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
        }
        composable(
            route = Screen.OwnerProfile.route
        ) {
            OwnerProfileScreen(navController = navController)
        }
        composable(
            route = Screen.OwnerAppointment.route
        ) {
            val ownerAppointmentVM: OwnerAppointmentViewModel = OwnerAppointmentViewModel(appointmentRepository = AppointmentRepository())
            OwnerAppointmentScreen(navController = navController, ownerAppointmentVM = ownerAppointmentVM)
        }
        composable(
            route = Screen.OwnerAppointmentDetail.route,
            arguments = listOf(navArgument(ARGUMENT_KEY_ID) {
                type = NavType.StringType
            })
        ) {
            val ownerAppointmentDetailVM: OwnerAppointmentDetailViewModel = OwnerAppointmentDetailViewModel(appointmentRepository = AppointmentRepository(), customerRepository = CustomerRepository())
            OwnerAppointmentDetailScreen(navController = navController, ownerAppointmentDetailVM = ownerAppointmentDetailVM, appointmentId = it.arguments?.getString(ARGUMENT_KEY_ID).toString())
        }

    }
}