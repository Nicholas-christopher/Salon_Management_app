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
class OwnerCustomerHistoryUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var ownerCustomerHistoryViewModel: OwnerCustomerHistoryViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        ownerCustomerHistoryViewModel = OwnerCustomerHistoryViewModel(appointmentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerCustomerHistory - flow - get customer history`()  {
        runTest {
            Mockito.`when`(appointmentRepository.getCustomerAppointmentHistory("1"))
                .thenReturn(Result.Success(listOf<Appointment>()))

            ownerCustomerHistoryViewModel.getCustomerAppointmentHistory("1")

            Assert.assertEquals("SUCCESS", ownerCustomerHistoryViewModel.appointmentsResult)
            Assert.assertEquals(listOf<Appointment>(), ownerCustomerHistoryViewModel.appointments)
        }
    }
}