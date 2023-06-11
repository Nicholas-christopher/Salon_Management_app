package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.repository.AppointmentRepository
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
class CustomerLandingUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var customerLandingViewModel: CustomerLandingViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        customerLandingViewModel = CustomerLandingViewModel(appointmentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `customerLanding - flow - positive`() {
        runTest {
            Mockito.`when`(appointmentRepository.getCustomerAppointments("1")).thenReturn(com.exam.salonmanagementapp.repository.Result.Success(
                listOf<Appointment>()
            ))

            customerLandingViewModel.getTodayAppointments("1")

            Assert.assertEquals("SUCCESS", customerLandingViewModel.appointmentsResult)
            Assert.assertEquals(listOf<Appointment>(), customerLandingViewModel.appointments)
        }
    }

}