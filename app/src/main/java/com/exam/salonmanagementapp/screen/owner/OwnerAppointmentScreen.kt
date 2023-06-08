package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun  OwnerAppointmentScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {

        var appointments by remember { mutableStateOf(listOf<Appointment>()) }
        val db = Firebase.firestore
        db.collection(DataConstant.TABLE_APPOINTMENT)
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
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Back"
                    )
                }
            }


            DisplayAppointments(navController, appointments)
        }
    }
}

@Composable
fun DisplayAppointments(
    navController: NavController,
    appointment: List<Appointment>
) {
    if (appointment.isNotEmpty()) {
        for (appointment in appointment) {
            CustomAppointmentList(
                navController = navController,
                appointment = appointment)
        }
    }

}

@Composable
@Preview(showBackground = true)
fun OwnerAppointmentScreenPreview() {
    OwnerAppointmentScreen(
        navController = rememberNavController()
    )
}