package com.exam.salonmanagementapp.screen.customer

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomDatePicker
import com.exam.salonmanagementapp.component.CustomDropDownMenu
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.component.CustomerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Appointment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.UUID

@Composable
fun CustomerAppointmentScreen(
    navController: NavController
){
    val context = LocalContext.current

    var appointmentDate by rememberSaveable { mutableStateOf("") }
    val todayCalendar = Calendar.getInstance()
    var time by rememberSaveable { mutableStateOf("") }
    var isTimeExpanded by remember{ mutableStateOf(false) }
    var service by rememberSaveable { mutableStateOf("") }
    var isServiceExpanded by remember{ mutableStateOf(false) }
    var description by rememberSaveable {mutableStateOf("")}

    var validateAppointmentDate by rememberSaveable { mutableStateOf(true) }
    var validateTime by rememberSaveable { mutableStateOf(true) }
    var validateService by rememberSaveable { mutableStateOf(true) }


    fun validateData(appointmentDate: String, time: String, service: String): Boolean {

        return true
    }

    fun saveAppointment(appointmentDate: String, time: String, service: String, description: String) {
        if (validateData(appointmentDate, time, service)){
            val db = Firebase.firestore
            val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
            val customerId = sharedPreference.getString("customerId", null)
            if (customerId != null) {
                val appointment = Appointment(customerId, appointmentDate, time, service, description)
                db.collection(DataConstant.TABLE_APPOINTMENT)
                    .add(appointment)
                    .addOnSuccessListener {
                        navController.popBackStack()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Make appointment failed!", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

    CustomerBackground (
        navController = navController
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(), contentAlignment = Alignment.TopCenter
        ){
            Column {

                CustomDatePicker(
                    value = appointmentDate,
                    onValueChange = { appointmentDate = it },
                    label = "Appointment Date",
                    leadingIconImageVector = Icons.Default.EditCalendar,
                    curYear = todayCalendar.get(Calendar.YEAR),
                    curMonth = todayCalendar.get(Calendar.MONTH),
                    curDay = todayCalendar.get(Calendar.DAY_OF_MONTH),
                )

                CustomDropDownMenu(
                    label = "Time",
                    services = listOf("10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00"),
                    isExpanded = isTimeExpanded,
                    onExpanded = { isTimeExpanded = it },
                    selectedItem = time,
                    onValueChange = { time = it },
                    leadingIconImageVector = Icons.Default.WatchLater,
                    showError = !validateTime,
                )

                CustomDropDownMenu(
                    label = "Services",
                    services = listOf("Hair Treatment","Spa","Perm","HairCut","Wash and Blow Dry"),
                    isExpanded = isServiceExpanded,
                    onExpanded = { isServiceExpanded = it },
                    selectedItem = service,
                    onValueChange = { service = it },
                    leadingIconImageVector = Icons.Default.ContentCut,
                    showError = !validateService,
                )

                CustomTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description",
                    leadingIconImageVector = Icons.Default.Description,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
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
                                text = "Cancel"
                            )
                        }
                        Button(
                            onClick = {
                                saveAppointment(appointmentDate, time, service, description)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                        ) {
                            Text(
                                text = "Save"
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun CustomerAppointmentScreen() {
    CustomerAppointmentScreen(
        navController = rememberNavController()
    )
}



