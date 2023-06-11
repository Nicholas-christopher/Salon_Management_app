package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
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
class OwnerAppointmentUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var ownerAppointmentViewModel: OwnerAppointmentViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        ownerAppointmentViewModel = OwnerAppointmentViewModel(appointmentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerAppointment - flow - positive - get appointments`() {
        runTest {
            Mockito.`when`(appointmentRepository.getAppointments())
                .thenReturn(Result.Success(listOf<Appointment>()))

            ownerAppointmentViewModel.getAppointments()

            Assert.assertEquals("SUCCESS", ownerAppointmentViewModel.appointmentsResult)
            Assert.assertEquals(listOf<Appointment>(), ownerAppointmentViewModel.appointments)

        }
    }
}