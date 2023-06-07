package com.exam.salonmanagementapp

sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object CustomerLanding: Screen(route = "customer_landing_screen")
    object CustomerAppointment: Screen(route = "customer_appointment_screen")
    object CustomerHistory: Screen(route = "customer_history_screen")
    object CustomerHistoryDetail: Screen(route = "customer_history_detail_screen/{id}")
    object CustomerProfile: Screen(route = "customer_profile_screen")
    object OwnerLanding: Screen(route = "owner_landing_screen")
    object OwnerPayment: Screen(route = "owner_payment_screen")
    object OwnerPaymentDetail: Screen(route = "owner_payment_detail_screen/{id}")
    object OwnerProduct: Screen(route = "owner_product_screen")
    object OwnerProductDetail: Screen(route = "owner_product_detail_screen/{id}")
    object OwnerProductAdd: Screen(route = "owner_product_add_screen")
    object OwnerCustomer: Screen(route = "owner_customer_screen")
    object OwnerCustomerDetail: Screen(route = "owner_customer_detail_screen/{id}")
    object OwnerCustomerPayment: Screen(route = "owner_customer_payment_screen/{id}")
    object OwnerProfile: Screen(route = "owner_profile_screen")
}
