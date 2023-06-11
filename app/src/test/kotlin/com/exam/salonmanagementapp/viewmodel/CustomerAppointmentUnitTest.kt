package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.repository.AppointmentRepository
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
class CustomerAppointmentUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var customerAppointmentViewModel: CustomerAppointmentViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        customerAppointmentViewModel = CustomerAppointmentViewModel(appointmentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `customerAppointment - field validation - positive`()  {
        customerAppointmentViewModel.appointmentDate = "11/6/2023"
        customerAppointmentViewModel.time = "08:00 - 10:00"
        customerAppointmentViewModel.service = "Hair Treatment"

        customerAppointmentViewModel.validateData()

        Assert.assertEquals(true, customerAppointmentViewModel.validateAppointmentDate)
        Assert.assertEquals(true, customerAppointmentViewModel.validateTime)
        Assert.assertEquals(true, customerAppointmentViewModel.validateService)
        Assert.assertEquals(true, customerAppointmentViewModel.validated)
    }

    @Test
    fun `customerAppointment - field validation - negative - missing appointment date`()  {
        //customerAppointmentViewModel.appointmentDate = "11/6/2023"
        customerAppointmentViewModel.time = "08:00 - 10:00"
        customerAppointmentViewModel.service = "Hair Treatment"

        customerAppointmentViewModel.validateData()

        Assert.assertEquals(false, customerAppointmentViewModel.validateAppointmentDate)
        Assert.assertEquals(true, customerAppointmentViewModel.validateTime)
        Assert.assertEquals(true, customerAppointmentViewModel.validateService)
        Assert.assertEquals(false, customerAppointmentViewModel.validated)
    }

    @Test
    fun `customerAppointment - field validation - negative - missing appointment time`()  {
        customerAppointmentViewModel.appointmentDate = "11/6/2023"
        //customerAppointmentViewModel.time = "08:00 - 10:00"
        customerAppointmentViewModel.service = "Hair Treatment"

        customerAppointmentViewModel.validateData()

        Assert.assertEquals(true, customerAppointmentViewModel.validateAppointmentDate)
        Assert.assertEquals(false, customerAppointmentViewModel.validateTime)
        Assert.assertEquals(true, customerAppointmentViewModel.validateService)
        Assert.assertEquals(false, customerAppointmentViewModel.validated)
    }

    @Test
    fun `customerAppointment - field validation - negative - missing service`()  {
        customerAppointmentViewModel.appointmentDate = "11/6/2023"
        customerAppointmentViewModel.time = "08:00 - 10:00"
        //customerAppointmentViewModel.service = "Hair Treatment"

        customerAppointmentViewModel.validateData()

        Assert.assertEquals(true, customerAppointmentViewModel.validateAppointmentDate)
        Assert.assertEquals(true, customerAppointmentViewModel.validateTime)
        Assert.assertEquals(false, customerAppointmentViewModel.validateService)
        Assert.assertEquals(false, customerAppointmentViewModel.validated)
    }

    @Test
    fun `customerLanding - flow - positive`() {
        runTest {
            customerAppointmentViewModel.appointmentDate = "11/6/2023"
            customerAppointmentViewModel.time = "08:00 - 10:00"
            customerAppointmentViewModel.service = "Hair Treatment"

            customerAppointmentViewModel.mapToAppointment("1")

            Mockito.`when`(appointmentRepository.makeAppointment(customerAppointmentViewModel.saveAppointment))
                .thenReturn(Result.Success(true))

            customerAppointmentViewModel.saveAppointment("1")

            Assert.assertEquals("SUCCESS", customerAppointmentViewModel.saveResult)
        }
    }
}