package com.exam.salonmanagementapp

sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object CustomerLanding: Screen(route = "customer_landing_screen")
}
