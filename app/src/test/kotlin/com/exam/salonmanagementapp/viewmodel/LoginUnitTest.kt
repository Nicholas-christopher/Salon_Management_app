package com.exam.salonmanagementapp.viewmodel

import com.exam.salonmanagementapp.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUnitTest {

    private val mainThreadSurrogate = newSingleThreadContext("RegistrationUnitTest thread")

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var loginViewModel: LoginViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        loginViewModel = LoginViewModel(customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
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
}