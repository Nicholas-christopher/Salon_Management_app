package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.CustomerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegistrationUnitTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var registrationViewModel: RegistrationViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        customerRepository = Mockito.mock(CustomerRepository::class.java)
        registrationViewModel = RegistrationViewModel(customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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

    @Test
    fun `registration - mapToCustomer - positive`() {
        registrationViewModel.name = "tester1"
        registrationViewModel.email = "test@gmail.com"
        registrationViewModel.phone = "0112223333"
        registrationViewModel.password = "T3st#123"
        registrationViewModel.confirmPassword = "T3st#123"
        registrationViewModel.mapToCustomer()

        assertEquals(registrationViewModel.name, registrationViewModel.saveCustomer.name)
        assertEquals(registrationViewModel.email, registrationViewModel.saveCustomer.email)
        assertEquals(registrationViewModel.phone, registrationViewModel.saveCustomer.phone)
        assertEquals(registrationViewModel.password, registrationViewModel.saveCustomer.password)
    }

    @Test
    fun `registration - flow - positive`() {
        runTest {
            registrationViewModel.name = "tester1"
            registrationViewModel.email = "test@gmail.com"
            registrationViewModel.phone = "0112223333"
            registrationViewModel.password = "T3st#123"
            registrationViewModel.confirmPassword = "T3st#123"
            registrationViewModel.mapToCustomer()
            val customer = Customer("tester1", "test@gmail.com", "0112223333", "T3st#1234")
            Mockito.`when`(customerRepository.register(registrationViewModel.saveCustomer)).thenReturn(com.exam.salonmanagementapp.repository.Result.Success(true))

            registrationViewModel.register()
            assertEquals("SUCCESS", registrationViewModel.registrationResult)
        }

    }
}