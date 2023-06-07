package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.OwnerBackground

@Composable
fun OwnerCustomerScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
        CustomListItem(
            leadingIconImageVector = Icons.Default.Person,
            title1 = "Nicholas",
            title2 = "Hair cut",
            title3 = "12/12/2023",
            leadingIconImageVector2 = Icons.Default.PanToolAlt
        )
        CustomListItem(
            leadingIconImageVector = Icons.Default.Person,
            title1 = "salohcin",
            title2 = "Perm",
            title3 = "11/12/2023",
            leadingIconImageVector2 = Icons.Default.PanToolAlt
        )
        Row() {

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