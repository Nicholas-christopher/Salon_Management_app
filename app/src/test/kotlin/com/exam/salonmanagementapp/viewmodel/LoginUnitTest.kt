package com.exam.salonmanagementapp.viewmodel

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Admin
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginUnitTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var loginViewModel: LoginViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        customerRepository = Mockito.mock(CustomerRepository::class.java)
        loginViewModel = LoginViewModel(customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `login - field validation - positive`()  {
        loginViewModel.email = "customer@gmail.com"
        loginViewModel.password = "T3st#123"

        loginViewModel.validateData()

        Assert.assertEquals(true, loginViewModel.validateEmail)
        Assert.assertEquals(true, loginViewModel.validatePassword)
        Assert.assertEquals(true, loginViewModel.validated)
    }

    @Test
    fun `login - field validation - negative - missing email`()  {
        //loginViewModel.email = "customer@gmail.com"
        loginViewModel.password = "T3st#123"

        loginViewModel.validateData()

        Assert.assertEquals(false, loginViewModel.validateEmail)
        Assert.assertEquals(true, loginViewModel.validatePassword)
        Assert.assertEquals(false, loginViewModel.validated)
    }

    @Test
    fun `login - field validation - negative - invalid email`()  {
        loginViewModel.email = "customer"
        loginViewModel.password = "T3st#123"

        loginViewModel.validateData()

        Assert.assertEquals(false, loginViewModel.validateEmail)
        Assert.assertEquals(true, loginViewModel.validatePassword)
        Assert.assertEquals(false, loginViewModel.validated)
    }

    @Test
    fun `login - field validation - negative - missing password`()  {
        loginViewModel.email = "customer@gmail.com"
        //loginViewModel.password = "T3st#123"

        loginViewModel.validateData()

        Assert.assertEquals(true, loginViewModel.validateEmail)
        Assert.assertEquals(false, loginViewModel.validatePassword)
        Assert.assertEquals(false, loginViewModel.validated)
    }

    @Test
    fun `login - flow - customer - positive`() {
        runTest {
            val sharedPrefsEditor = Mockito.mock(
                SharedPreferences.Editor::class.java
            )
            val sharedPrefs = Mockito.mock(
                SharedPreferences::class.java
            )
            val context = Mockito.mock(
                android.content.Context::class.java
            )
            Mockito.`when`(context.getSharedPreferences(anyString(), anyInt()))
                .thenReturn(sharedPrefs)
            Mockito.`when`(sharedPrefs.edit())
                .thenReturn(sharedPrefsEditor)


            loginViewModel.email = "customer@gmail.com"
            loginViewModel.password = "T3st#123"
            Mockito.`when`(customerRepository.login(loginViewModel.email, loginViewModel.password)).thenReturn(com.exam.salonmanagementapp.repository.Result.Success(Customer("1", "mock@gmail.com", "mock", "0112223333", "")))
            loginViewModel.login(context)

            Assert.assertEquals("SUCCESS_CUSTOMER", loginViewModel.loginResult)
        }

    }

    @Test
    fun `login - flow - admin - positive`() {
        runTest {
            val sharedPrefsEditor = Mockito.mock(
                SharedPreferences.Editor::class.java
            )
            val sharedPrefs = Mockito.mock(
                SharedPreferences::class.java
            )
            val context = Mockito.mock(
                android.content.Context::class.java
            )
            Mockito.`when`(context.getSharedPreferences(anyString(), anyInt()))
                .thenReturn(sharedPrefs)
            Mockito.`when`(sharedPrefs.edit())
                .thenReturn(sharedPrefsEditor)


            loginViewModel.email = "admin@gmail.com"
            loginViewModel.password = "T3st#123"
            Mockito.`when`(customerRepository.loginAdmin(loginViewModel.email, loginViewModel.password)).thenReturn(com.exam.salonmanagementapp.repository.Result.Success(
                Admin("admin@gmail.com", "")
            ))
            loginViewModel.login(context)

            Assert.assertEquals("SUCCESS_ADMIN", loginViewModel.loginResult)
        }

    }
}