package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.ProductRepository
import com.exam.salonmanagementapp.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OwnerCustomerUnitTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var ownerCustomerViewModel: OwnerCustomerViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        customerRepository = Mockito.mock(CustomerRepository::class.java)
        ownerCustomerViewModel = OwnerCustomerViewModel(customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerCustomer - flow - get customer`()  {
        runTest {
            Mockito.`when`(customerRepository.getCustomers())
                .thenReturn(Result.Success(listOf(Customer())))

            ownerCustomerViewModel.getCustomers()

            Assert.assertEquals("SUCCESS", ownerCustomerViewModel.customersResult)
            Assert.assertEquals(listOf(Customer()), ownerCustomerViewModel.customers)
        }
    }
}