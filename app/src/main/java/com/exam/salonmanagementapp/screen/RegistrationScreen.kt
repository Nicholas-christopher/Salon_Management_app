package com.exam.salonmanagementapp.screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Phone
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exam.salonmanagementapp.component.CustomTextField
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.data.Customer
import com.google.firebase.database.FirebaseDatabase

@Composable
fun RegistrationScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var validateName by rememberSaveable { mutableStateOf(true) }
    var validateEmail by rememberSaveable { mutableStateOf(true) }
    var validatePhone by rememberSaveable { mutableStateOf(true) }
    var validatePassword by rememberSaveable { mutableStateOf(true) }
    var validateConfirmPassword by rememberSaveable { mutableStateOf(true) }
    var validatePasswordEqual by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisisble by rememberSaveable { mutableStateOf(true) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(true) }

    fun validateData(name: String, email: String, phone: String, password: String, confirmPassword: String): Boolean {
        var passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$".toRegex()

        validateName = name.isNotBlank()
        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        validatePhone = Patterns.PHONE.matcher(phone).matches()
        validatePassword = passwordRegex.matches(password)
        validateConfirmPassword = passwordRegex.matches(password)
        validatePasswordEqual = password == confirmPassword

        return validateName && validateEmail && validatePhone && validatePassword && validateConfirmPassword &&  validatePasswordEqual
    }

    fun register(name: String, email: String, phone: String, password: String, confirmPassword: String) {
        if (validateData(name, email, phone, password, confirmPassword)){
            var database = FirebaseDatabase.getInstance().getReference("Customers")
            val customer = Customer(email, name, phone, password)
            database.child(email).setValue(customer).addOnSuccessListener {
                Toast.makeText(context, "Registration completed", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }.addOnFailureListener {
                Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    val displayMetrics = context.resources.displayMetrics
    val dpHeight = displayMetrics.heightPixels / displayMetrics.density
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.salon_bg),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
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

            Text(
                text = "Register to our app",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 20.dp),
                color = Color.Blue
            )
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                showError = !validateEmail,
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
                value = name,
                onValueChange = { name = it },
                label = "Name",
                showError = !validateName,
                errorMessage = context.resources.getString(R.string.validate_name_error),
                leadingIconImageVector = Icons.Default.PermIdentity,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            CustomTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Telp No",
                showError = !validatePhone,
                errorMessage = context.resources.getString(R.string.validate_phone_error),
                leadingIconImageVector = Icons.Default.Phone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                showError = !validatePassword,
                errorMessage = context.resources.getString(R.string.validate_password_error),
                isPasswordField = true,
                isPasswordVisible = isPasswordVisisble,
                onVisibilityChange = { isPasswordVisisble = it },
                leadingIconImageVector = Icons.Default.Password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                showError = !validateConfirmPassword,
                errorMessage = context.resources.getString(R.string.validate_confirmPassword_error),
                isPasswordField = true,
                isPasswordVisible = isConfirmPasswordVisible,
                onVisibilityChange = { isConfirmPasswordVisible = it },
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
                    register(name, email, phone, password, confirmPassword)
                },
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 20.dp)
                    .fillMaxWidth(0.9f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
            ) {
                Text(
                    text = "Register"
                )
            }
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 10.dp)
                    .fillMaxWidth(0.9f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White)
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview() {
    RegistrationScreen(navController = rememberNavController())
}