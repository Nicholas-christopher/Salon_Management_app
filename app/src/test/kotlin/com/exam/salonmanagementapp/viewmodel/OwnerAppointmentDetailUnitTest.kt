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
class OwnerAppointmentDetailUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var customerRepository: CustomerRepository
    private lateinit var ownerAppointmentDetailViewModel: OwnerAppointmentDetailViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        customerRepository = Mockito.mock(CustomerRepository::class.java)
        ownerAppointmentDetailViewModel = OwnerAppointmentDetailViewModel(appointmentRepository, customerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerAppointmentDetail - flow - positive - get appointment`() {
        runTest {
            val appointment = Appointment()
            Mockito.`when`(appointmentRepository.getAppointment("1"))
                .thenReturn(Result.Success(appointment))

            ownerAppointmentDetailViewModel.getAppointment("1")

            Assert.assertEquals("SUCCESS", ownerAppointmentDetailViewModel.appointmentResult)
            Assert.assertEquals(appointment, ownerAppointmentDetailViewModel.appointment)

        }
    }

    @Test
    fun `ownerAppointmentDetail - flow - positive - get customer`() {
        runTest {
            val customer = Customer()
            Mockito.`when`(customerRepository.getCustomer("1"))
                .thenReturn(Result.Success(customer))

            ownerAppointmentDetailViewModel.getCustomer("1")

            Assert.assertEquals("SUCCESS", ownerAppointmentDetailViewModel.customerResult)
            Assert.assertEquals(customer, ownerAppointmentDetailViewModel.customer)

        }
    }

    @Test
    fun `ownerAppointmentDetail - flow - positive - delete appointment`() {
        runTest {
            Mockito.`when`(appointmentRepository.deleteAppointment("1"))
                .thenReturn(Result.Success(true))

            ownerAppointmentDetailViewModel.deleteAppointment("1")

            Assert.assertEquals("SUCCESS", ownerAppointmentDetailViewModel.deleteAppointmentResult)

        }
    }
}