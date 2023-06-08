package com.exam.salonmanagementapp.screen.owner;

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.CustomProductItem
import com.exam.salonmanagementapp.component.OwnerBackground
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun  OwnerProductScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
        var products by remember { mutableStateOf(listOf<Product>()) }

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
                        navController.navigate(route = Screen.OwnerProductAdd.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Add Product"
                    )
                }
            }

            val db = Firebase.firestore
            db.collection(DataConstant.TABLE_PRODUCT)
                .get()
                .addOnSuccessListener { documents ->
                    System.out.println("documents.size() => " + documents.size())
                    if (!documents.isEmpty) {
                        products = documents.toObjects<Product>()

                    }
                }

            DisplayProducts(navController, products)
        }
    }
}

@Composable
fun DisplayProducts(
    navController: NavController,
    products: List<Product>
) {
    if (products.isNotEmpty()) {
        for (product in products) {
            CustomProductItem(
                navController = navController,
                product = product)
        }
    }

}


@Composable
@Preview(showBackground = true)
fun OwnerProductScreenPreview() {
    OwnerProductScreen(
        navController = rememberNavController()
    )
}