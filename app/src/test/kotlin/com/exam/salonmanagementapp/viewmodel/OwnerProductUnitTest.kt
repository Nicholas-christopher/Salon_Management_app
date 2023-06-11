package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.PaymentRepository
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
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OwnerProductUnitTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var ownerProductViewModel: OwnerProductViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepository = Mockito.mock(ProductRepository::class.java)
        ownerProductViewModel = OwnerProductViewModel(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerProduct - flow - get product listing`()  {
        runTest {
            Mockito.`when`(productRepository.getProducts())
                .thenReturn(Result.Success(listOf(Product())))

            ownerProductViewModel.getProducts()

            Assert.assertEquals("SUCCESS", ownerProductViewModel.productsResult)
            Assert.assertEquals(listOf(Product()), ownerProductViewModel.products)
        }
    }
}