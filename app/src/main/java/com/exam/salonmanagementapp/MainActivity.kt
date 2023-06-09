package com.exam.salonmanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.ui.theme.SalonManagementAppTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalonManagementAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SalonManagementAppTheme {
        SetupNavGraph(
            navController = rememberNavController()
        )
    }
}
