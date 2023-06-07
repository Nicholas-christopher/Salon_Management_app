package com.exam.salonmanagementapp.screen.customer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.component.CustomListItem
import com.exam.salonmanagementapp.component.CustomerBackground
import com.exam.salonmanagementapp.component.TitleText

@Composable
fun CustomerLandingScreen(
    navController: NavController
) {
    CustomerBackground (
        navController = navController
    ) {
        CustomListItem(
            leadingIconImageVector = Icons.Default.EditCalendar,
            title1 = "12/23/2023",
            title2 = "haircut",
            title3 = "12:00 - 14:00",
            leadingIconImageVector2 = Icons.Default.Delete
        )
        CustomListItem(
            leadingIconImageVector = Icons.Default.EditCalendar,
            title1 = "12/23/2023",
            title2 = "haircut",
            title3 = "12:00 - 14:00",
            leadingIconImageVector2 = Icons.Default.Delete
        )
        CustomListItem(
            leadingIconImageVector = Icons.Default.EditCalendar,
            title1 = "12/23/2023",
            title2 = "haircut",
            title3 = "12:00 - 14:00",
            leadingIconImageVector2 = Icons.Default.Delete
        )
        CustomListItem(
            leadingIconImageVector = Icons.Default.EditCalendar,
            title1 = "12/23/2023",
            title2 = "haircut",
            title3 = "12:00 - 14:00",
            leadingIconImageVector2 = Icons.Default.Delete
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(), contentAlignment = Alignment.TopCenter
        ) {

        }

    }
}


@Composable
@Preview(showBackground = true)
fun CustomerLandingScreenPreview() {
    CustomerLandingScreen(
        navController = rememberNavController()
    )
}