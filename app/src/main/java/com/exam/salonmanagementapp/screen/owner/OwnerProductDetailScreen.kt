package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.component.OwnerBackground

@Composable
fun OwnerProductDetailScreen (
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
        var description by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Filled.Image,
                    contentDescription ="",
                    modifier = Modifier.size(100.dp) )

            }

            Text(
                text = "Product Quantity : 15",
                modifier = Modifier
                    .padding(vertical = 10.dp)

            )

            CustomTextField(
                value = description,
                onValueChange = { description = it },
                label = "Product Used",
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

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(
                            text = "Use Product"
                        )
                    }
                }
            }
            
        }
    }
}


@Composable
@Preview(showBackground = true)
fun OwnerProductDetailScreenPreview() {
    OwnerProductDetailScreen(
        navController = rememberNavController()
    )
}