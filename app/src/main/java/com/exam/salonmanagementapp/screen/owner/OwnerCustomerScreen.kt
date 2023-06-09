package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.CustomCustomerDetails
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.component.OwnerBottomBar
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.viewmodel.OwnerCustomerViewModel

@Composable
fun OwnerCustomerScreen(
    navController: NavController,
    ownerCustomerVM: OwnerCustomerViewModel
) {
    val context = LocalContext.current
    var customerName by rememberSaveable { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = {Text("TopAppBar")}) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = {}){
            Text("X")
        } },
        drawerContent = { Text(text = "drawerContent") },
        content = { padding ->
            OwnerBackground (
                navController = navController
            ) {
                Text(text = context.resources.getString(R.string.owner_customer_list_title))

                CustomTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = "Customer Name",
                    leadingIconImageVector = Icons.Default.PermIdentity,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                Button(
                    onClick = {
                        //saveProduct(productName, quantity, useImageUri, imageUri)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Add Product"
                    )
                }

                when (ownerCustomerVM.customersResult) {
                    "" -> ownerCustomerVM.getCustomerList()
                    "LOADING" -> CircularProgressIndicator()
                    "SUCCESS" -> DisplayCustomer(navController, ownerCustomerVM.customers)
                }
            }
        },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )
}

@Composable
fun DisplayCustomer(
    navController: NavController,
    customer: List<Customer>
) {
    if (customer.isNotEmpty()) {
        for (customer in customer) {
            Column(Modifier.padding(bottom = 10.dp)) {
                CustomCustomerDetails(
                    navController = navController,
                    customer = customer)

            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun OwnerCustomerScreenPreview() {
    OwnerCustomerScreen(
        navController = rememberNavController(),
        ownerCustomerVM = viewModel()
    )
}