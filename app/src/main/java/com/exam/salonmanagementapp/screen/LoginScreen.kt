package com.exam.salonmanagementapp.screen

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.constant.DataConstant
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.viewmodel.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


@Composable
fun LoginScreen(
    navController: NavController,
    loginVM: LoginViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var isPasswordVisisble by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.salon_bg),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollState),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(
                    color = Color.White.copy(alpha = 0.6f)
                )
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomTextField(
                value = loginVM.email,
                onValueChange = { loginVM.email = it },
                label = "Email",
                showError = !loginVM.validateEmail,
                errorMessage = context.resources.getString(R.string.validate_email_error),
                leadingIconImageVector = Icons.Default.AlternateEmail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            CustomTextField(
                value = loginVM.password,
                onValueChange = { loginVM.password = it },
                label = "Password",
                showError = !loginVM.validatePassword,
                errorMessage = context.resources.getString(R.string.validate_password_error),
                isPasswordField = true,
                isPasswordVisible = isPasswordVisisble,
                onVisibilityChange = { isPasswordVisisble = it },
                leadingIconImageVector = Icons.Default.Password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus( ) }
                )
            )
            Button(
                onClick = {
                    loginVM.login(context)
                },
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 20.dp)
                    .fillMaxWidth(0.9f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
            ) {
                Text(
                    text = "Login"
                )
            }
            Divider(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Don't have account? Register here",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        navController.navigate(route = Screen.Registration.route)
                    }
                )
        }
        //System.out.println("loginVM.loginResult => ${loginVM.loginResult}")
        when (loginVM.loginResult) {
            "LOADING" -> {
                CircularProgressIndicator()
            }
            "SUCCESS_ADMIN" -> {
                navController.navigate(route = Screen.OwnerLanding.route)
            }
            "SUCCESS_CUSTOMER" -> {
                navController.navigate(route = Screen.CustomerLanding.route)
            }
            "FAILED" -> {
                Toast.makeText(context, "Invalid username or password!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        loginVM = viewModel()
    )
}