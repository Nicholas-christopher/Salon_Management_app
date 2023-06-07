package com.exam.salonmanagementapp.screen.owner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.OwnerBackground

@Composable
fun OwnerProfileScreen(
    navController: NavController
) {
    OwnerBackground (
        navController = navController
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            /*
            ProfileImage(
                imageResId = R.drawable.profile,
                modifier = Modifier.align(Alignment.TopStart)
            )
             */
        }

    }
}


@Composable
@Preview(showBackground = true)
fun OwnerProfileScreenPreview() {
    OwnerProfileScreen(
        navController = rememberNavController()
    )
}