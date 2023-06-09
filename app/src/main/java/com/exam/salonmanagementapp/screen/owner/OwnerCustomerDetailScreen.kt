package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.CustomAppointments
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun  OwnerCustomerDetailScreen(
    navController: NavController,
    customerId: String,

) {
    OwnerBackground (
        navController = navController
    ) {
        var customerName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var appointments by remember { mutableStateOf(listOf<Appointment>()) }



        val db = Firebase.firestore
        db.collection(DataConstant.TABLE_CUSTOMER)
            .whereEqualTo("id", customerId)
            .get()
            .addOnSuccessListener { documents ->
                System.out.println("documents.size() => " + documents.size())
                if (!documents.isEmpty) {
                    val customer = documents.first().toObject<Customer>()
                    customerName = customer.name
                    email = customer.email
                    phone = customer.phone
                }
            }

        db.collection(DataConstant.TABLE_APPOINTMENT)
            .whereEqualTo("customerId",customerId)
            .get()
            .addOnSuccessListener { documents ->
                System.out.println("documents.size() => " + documents.size())
                if (!documents.isEmpty) {
                    appointments = documents.toObjects<Appointment>()

                }
            }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Customer Name : $customerName",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )

            Text(
                text = "Customer Email :  $email",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )
            Text(
                text = "Customer PhoneNumber :  $phone",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )
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
            }
        }

        CustomAppointments(navController, appointments)
    }
}

@Composable
@Preview(showBackground = true)
fun OwnerCustomerDetailScreenPreview() {
    OwnerCustomerDetailScreen(
        navController = rememberNavController(),
        customerId = "1",
    )
}

