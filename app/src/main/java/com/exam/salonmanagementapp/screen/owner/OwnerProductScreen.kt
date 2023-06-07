package com.exam.salonmanagementapp.screen.owner;

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.OwnerBackground

@Composable
fun  OwnerProductScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
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
                        navController.navigate(route = Screen.OwnerProductDetail.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text(
                        text = "Use Product"
                    )
                }
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
        }
            CustomListItem(
                leadingIconImageVector = Icons.Default.Inventory,
                title1 = "12",
                title2 = "Shampoo",
                title3 = "bla bla",
                leadingIconImageVector2 = Icons.Default.Delete
            )
            CustomListItem(
                leadingIconImageVector = Icons.Default.Inventory,
                title1 = "15",
                title2 = "Conditioner",
                title3 = "bla bla",
                leadingIconImageVector2 = Icons.Default.Delete
            )
    }
}


@Composable
@Preview(showBackground = true)
fun OwnerProductScreenPreview() {
    OwnerProductScreen(
        navController = rememberNavController()
    )
}