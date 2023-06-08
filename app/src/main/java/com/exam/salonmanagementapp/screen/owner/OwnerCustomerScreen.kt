package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.CustomAppointmentList
import com.exam.salonmanagementapp.component.CustomCustomerDetails
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun OwnerCustomerScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {

        var customer by remember { mutableStateOf(listOf<Customer>()) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Back"
                    )
                }
            }

            val db = Firebase.firestore
            db.collection(DataConstant.TABLE_CUSTOMER)
                .get()
                .addOnSuccessListener { documents ->
                    System.out.println("documents.size() => " + documents.size())
                    if (!documents.isEmpty) {
                        customer = documents.toObjects<Customer>()

                    }
                }

            DisplayCustomer(navController, customer)
        }
    }
}

@Composable
fun DisplayCustomer(
    navController: NavController,
    customer: List<Customer>
) {
    if (customer.isNotEmpty()) {
        for (customer in customer) {
            CustomCustomerDetails(
                navController = navController,
                customer = customer)
        }
    }

}


@Composable
@Preview(showBackground = true)
fun OwnerCustomerScreenPreview() {
    OwnerCustomerScreen(
        navController = rememberNavController()
    )
}