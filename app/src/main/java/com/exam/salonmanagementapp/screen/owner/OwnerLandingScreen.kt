package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.CustomerBackground
import com.exam.salonmanagementapp.component.OwnerBackground


@Composable
fun OwnerLandingScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
        CustomListItem(
            leadingIconImageVector = Icons.Default.Inventory,
            title1 = "Nicholas",
            title2 = "Hair cut",
            title3 = "12/12/2023",
            leadingIconImageVector2 = Icons.Default.PanToolAlt
        )
        CustomListItem(
                leadingIconImageVector = Icons.Default.Inventory,
        title1 = "test",
        title2 = "Perm",
        title3 = "12/12/2023",
        leadingIconImageVector2 = Icons.Default.PanToolAlt
        )
        CustomListItem(
            leadingIconImageVector = Icons.Default.Inventory,
            title1 = "Chris",
            title2 = "Spa",
            title3 = "12/12/2023",
            leadingIconImageVector2 = Icons.Default.PanToolAlt
        )

        Row() {
            
        }
    }
}


@Composable
@Preview(showBackground = true)
fun OwnerLandingScreenPreview() {
    OwnerLandingScreen(
        navController = rememberNavController()
    )
}