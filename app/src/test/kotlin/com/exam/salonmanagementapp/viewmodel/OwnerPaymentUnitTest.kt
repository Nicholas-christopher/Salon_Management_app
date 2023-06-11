package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.repository.AppointmentRepository
import com.exam.salonmanagementapp.repository.CustomerRepository
import com.exam.salonmanagementapp.repository.PaymentRepository
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
class OwnerPaymentUnitTest {

    @Mock
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var ownerPaymentViewModel: OwnerPaymentViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        appointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        paymentRepository = Mockito.mock(PaymentRepository::class.java)
        ownerPaymentViewModel = OwnerPaymentViewModel(appointmentRepository, paymentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerPayment - field validation - positive`()  {
        ownerPaymentViewModel.amount = "10.05"

        ownerPaymentViewModel.validateData()

        Assert.assertEquals(true, ownerPaymentViewModel.validateAmount)
        Assert.assertEquals(true, ownerPaymentViewModel.validated)
    }

    @Test
    fun `ownerPayment - field validation - negative - missing amount`()  {
        //ownerPaymentViewModel.amount = "10.05"

        ownerPaymentViewModel.validateData()

        Assert.assertEquals(false, ownerPaymentViewModel.validateAmount)
        Assert.assertEquals(false, ownerPaymentViewModel.validated)
    }

    @Test
    fun `ownerPayment - field validation - negative - invalid amount`()  {
        ownerPaymentViewModel.amount = "abcd"

        ownerPaymentViewModel.validateData()

        Assert.assertEquals(false, ownerPaymentViewModel.validateAmount)
        Assert.assertEquals(false, ownerPaymentViewModel.validated)
    }

    @Test
    fun `ownerPayment - flow - make payment`()  {
        runTest {
            val appointment = Appointment("1", "1", Date(), "08:00 - 10:00", "Hair Treatment", "", "NEW", 0f)
            ownerPaymentViewModel.amount = "10.05"
            ownerPaymentViewModel.mapToPayment(appointment.customerId, appointment.appointmentId)
            ownerPaymentViewModel.mapToAppointment(appointment)

            Mockito.`when`(appointmentRepository.completeAppointment(ownerPaymentViewModel.saveAppointment))
                .thenReturn(Result.Success(true))
            Mockito.`when`(paymentRepository.makePayment(ownerPaymentViewModel.savePayment))
                .thenReturn(Result.Success(true))

            ownerPaymentViewModel.makePayment(appointment.customerId, appointment.appointmentId, appointment)

            Assert.assertEquals("SUCCESS", ownerPaymentViewModel.appointmentResult)
            Assert.assertEquals("SUCCESS", ownerPaymentViewModel.paymentResult)
        }
    }
}