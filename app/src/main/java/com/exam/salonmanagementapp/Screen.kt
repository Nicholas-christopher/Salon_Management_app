package com.exam.salonmanagementapp

const val ARGUMENT_KEY_ID = "id"

sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object CustomerLanding: Screen(route = "customer_landing_screen")
    object CustomerAppointment: Screen(route = "customer_appointment_screen")
    object CustomerHistory: Screen(route = "customer_history_screen")
    object CustomerHistoryDetail: Screen(route = "customer_history_detail_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_history_detail_screen/$id"
        }
    }
    object CustomerProfile: Screen(route = "customer_profile_screen")
    object OwnerLanding: Screen(route = "owner_landing_screen")
    object OwnerPayment: Screen(route = "owner_payment_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_payment_screen/$id"
        }
    }
    object OwnerPaymentDetail: Screen(route = "owner_payment_detail_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_payment_detail_screen/$id"
        }
    }
    object OwnerProduct: Screen(route = "owner_product_screen")
    object OwnerProductDetail: Screen(route = "owner_product_detail_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_product_detail_screen/$id"
        }
    }
    object OwnerProductAdd: Screen(route = "owner_product_add_screen")
    object OwnerCustomer: Screen(route = "owner_customer_screen")
    object OwnerCustomerDetail: Screen(route = "owner_customer_detail_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_customer_detail_screen/$id"
        }
    }
    object OwnerCustomerHistory: Screen(route = "owner_customer_history_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_customer_history_screen/$id"
        }
    }
    object OwnerProfile: Screen(route = "owner_profile_screen")
    object OwnerAppointment: Screen(route = "owner_appointment_screen")

    object OwnerAppointmentDetail: Screen(route = "owner_appointment_detail_screen/{$ARGUMENT_KEY_ID}") {
        fun passId(id: String): String{
            return "owner_appointment_detail_screen/$id"
        }
    }
}
