package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
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
class OwnerCustomerDetailUnitTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var ownerCustomerDetailViewModel: OwnerCustomerDetailViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        customerRepository = Mockito.mock(CustomerRepository::class.java)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        ownerCustomerDetailViewModel = OwnerCustomerDetailViewModel(customerRepository, appointmentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerCustomerDetail - flow - get customer detail`()  {
        runTest {
            val customer = Customer()
            Mockito.`when`(customerRepository.getCustomer("1"))
                .thenReturn(Result.Success(customer))

            ownerCustomerDetailViewModel.getCustomer("1")

            Assert.assertEquals("SUCCESS", ownerCustomerDetailViewModel.customerResult)
            Assert.assertEquals(customer, ownerCustomerDetailViewModel.customer)
        }
    }

    @Test
    fun `ownerCustomerDetail - flow - get customer appointments`()  {
        runTest {
            Mockito.`when`(appointmentRepository.getCustomerAppointments("1"))
                .thenReturn(Result.Success(listOf<Appointment>()))

            ownerCustomerDetailViewModel.getCustomerAppointments("1")

            Assert.assertEquals("SUCCESS", ownerCustomerDetailViewModel.appointmentsResult)
            Assert.assertEquals(listOf<Appointment>(), ownerCustomerDetailViewModel.appointments)
        }
    }
}