package com.exam.salonmanagementapp.viewmodel

import android.util.Patterns
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val customerRepository : CustomerRepository
) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var validateName by mutableStateOf(true)
        private set
    var validateEmail by  mutableStateOf(true)
        private set
    var validatePhone by mutableStateOf(true)
        private set
    var validatePassword by mutableStateOf(true)
        private set
    var validateConfirmPassword by mutableStateOf(true)
        private set
    var validatePasswordEqual by mutableStateOf(true)
        private set

    var validated by mutableStateOf(false)
        private set
    var registrationResult by mutableStateOf("")

    fun validateData() {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$".toRegex()

        validateName = name.isNotBlank()
        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        validatePhone = Patterns.PHONE.matcher(phone).matches()
        validatePassword = passwordRegex.matches(password)
        validateConfirmPassword = passwordRegex.matches(password)
        validatePasswordEqual = password == confirmPassword

        validated = validateName && validateEmail && validatePhone && validatePassword && validateConfirmPassword &&  validatePasswordEqual
    }

    fun register() {
        viewModelScope.launch {
            registrationResult = "LOADING"
            validateData()
            if (validated) {
                val customer = Customer(UUID.randomUUID().toString(), email, name, phone, password)
                val result = try {
                    customerRepository.register(customer)
                } catch(e: Exception) {
                    Result.Error(Exception("Network request failed"))
                }
                when (result) {
                    is Result.Success -> {
                        registrationResult = "SUCCESS"
                    }
                    else -> {
                        registrationResult = "FAILED"
                    }
                }
            }
        }
    }
}