package com.exam.salonmanagementapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.viewmodel.RegistrationViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.internal.wait
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrationUnitTest {

    private val mainThreadSurrogate = newSingleThreadContext("RegistrationUnitTest thread")

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var registrationViewModel: RegistrationViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        registrationViewModel = RegistrationViewModel(customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `registration - field validation - positive`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(true, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing name`()  {
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(false, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing email`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(false, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - invalid email`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "tester1"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(false, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing phone`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(false, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - invalid phone`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "abcd"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(false, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(true, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing password`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(false, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing lower case letter`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3ST#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(false, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing upper case letter`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "t3st#123"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(false, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing numeric letter`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "Test#ABC"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(false, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - missing special character letter`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "Test1ABC"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(false, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }

    @Test
    fun `registration - field validation - negative - mismtach password and confirm password`()  {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#1234"
        registrationViewModel.confirmPassword = "T3st#123"

        registrationViewModel.validateData()

        assertEquals(true, registrationViewModel.validateName)
        assertEquals(true, registrationViewModel.validateEmail)
        assertEquals(true, registrationViewModel.validatePhone)
        assertEquals(true, registrationViewModel.validatePassword)
        assertEquals(true, registrationViewModel.validateConfirmPassword)
        assertEquals(false, registrationViewModel.validatePasswordEqual)
        assertEquals(false, registrationViewModel.validated)
    }
}