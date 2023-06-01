package com.exam.salonmanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.ui.theme.SalonManagementAppTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalonManagementAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
                /*
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
                */
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
