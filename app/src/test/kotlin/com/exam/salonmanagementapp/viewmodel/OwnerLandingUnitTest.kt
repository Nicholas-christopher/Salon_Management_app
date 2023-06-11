package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Product
import com.exam.salonmanagementapp.repository.AppointmentRepository
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
class OwnerLandingUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var ownerLandingViewModel: OwnerLandingViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        productRepository = Mockito.mock(ProductRepository::class.java)
        ownerLandingViewModel = OwnerLandingViewModel(appointmentRepository, productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerLanding - flow - positive - get upcoming appointments`() {
        runTest {
            Mockito.`when`(appointmentRepository.getUpcomingAppointments())
                .thenReturn(Result.Success(listOf<Appointment>()))

            ownerLandingViewModel.getTodayAppointments()

            Assert.assertEquals("SUCCESS", ownerLandingViewModel.appointmentsResult)
            Assert.assertEquals(listOf<Appointment>(), ownerLandingViewModel.appointments)
        }
    }

    @Test
    fun `ownerLanding - flow - positive - get low stock supply`() {
        runTest {
            Mockito.`when`(productRepository.getLowStockProducts())
                .thenReturn(Result.Success(listOf<Product>()))

            ownerLandingViewModel.getLowStockProducts()

            Assert.assertEquals("SUCCESS", ownerLandingViewModel.productssResult)
            Assert.assertEquals(listOf<Product>(), ownerLandingViewModel.products)
        }
    }

}