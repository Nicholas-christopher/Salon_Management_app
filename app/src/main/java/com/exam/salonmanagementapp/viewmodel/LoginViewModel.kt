package com.exam.salonmanagementapp.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Admin
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val customerRepository : CustomerRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var validateEmail by mutableStateOf(true)
    var validatePassword by mutableStateOf(true)

    var validated by mutableStateOf(false)
        private set
    var loginResult by mutableStateOf("")
    var customer by mutableStateOf(Customer())
    var admin by mutableStateOf(Admin())


    fun validateData() {
        validateEmail = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        validatePassword = password.isNotBlank()

        validated = validateEmail && validatePassword
    }

    fun login(context: Context) {
        viewModelScope.launch {
            loginResult = "LOADING"
            validateData()
            if (validated) {
                if (email.contains("admin")) {
                    val result = try {
                        customerRepository.loginAdmin(email, password)
                    } catch(e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }
                    when (result) {
                        is Result.Success -> {
                            admin = result.data

                            val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
                            var editor = sharedPreference.edit()
                            editor.putString("adminId", admin.username)
                            editor.commit()

                            loginResult = "SUCCESS_ADMIN"
                        }
                        else -> {
                            loginResult = "FAILED"
                        }
                    }
                }
                else {
                    val result = try {
                        customerRepository.login(email, password)
                    } catch(e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }
                    when (result) {
                        is Result.Success -> {
                            customer = result.data

                            val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
                            var editor = sharedPreference.edit()
                            editor.putString("customerId", customer.id)
                            editor.putString("customerName", customer.name)
                            editor.commit()

                            loginResult = "SUCCESS_CUSTOMER"
                        }
                        else -> {
                            loginResult = "FAILED"
                        }
                    }
                }
            }
        }
    }
}