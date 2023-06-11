package com.exam.salonmanagementapp.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.net.UriCompat
import androidx.core.net.toUri
import com.exam.salonmanagementapp.data.Appointment
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
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OwnerProductAddUnitTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var ownerProductAddViewModel: OwnerProductAddViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepository = Mockito.mock(ProductRepository::class.java)
        ownerProductAddViewModel = OwnerProductAddViewModel(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerProductAdd - field validation - positive`() {
        ownerProductAddViewModel.productName = "test"
        ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
        ownerProductAddViewModel.quantity = "10"

        ownerProductAddViewModel.validateData()

        Assert.assertEquals(true, ownerProductAddViewModel.validateProductName)
        Assert.assertEquals(true, ownerProductAddViewModel.validateImageUri)
        Assert.assertEquals(true, ownerProductAddViewModel.validateQuantity)
        Assert.assertEquals(true, ownerProductAddViewModel.validated)
    }

    @Test
    fun `ownerProductAdd - field validation - negative - missing product name`() {
        //ownerProductAddViewModel.productName = "test"
        ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
        ownerProductAddViewModel.quantity = "10"

        ownerProductAddViewModel.validateData()

        Assert.assertEquals(false, ownerProductAddViewModel.validateProductName)
        Assert.assertEquals(true, ownerProductAddViewModel.validateImageUri)
        Assert.assertEquals(true, ownerProductAddViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductAddViewModel.validated)
    }

    @Test
    fun `ownerProductAdd - field validation - negative - missing image uri`() {
        ownerProductAddViewModel.productName = "test"
        //ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
        ownerProductAddViewModel.quantity = "10"

        ownerProductAddViewModel.validateData()

        Assert.assertEquals(true, ownerProductAddViewModel.validateProductName)
        Assert.assertEquals(false, ownerProductAddViewModel.validateImageUri)
        Assert.assertEquals(true, ownerProductAddViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductAddViewModel.validated)
    }

    @Test
    fun `ownerProductAdd - field validation - negative - missing quantity`() {
        ownerProductAddViewModel.productName = "test"
        ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
        //ownerProductAddViewModel.quantity = "10"

        ownerProductAddViewModel.validateData()

        Assert.assertEquals(true, ownerProductAddViewModel.validateProductName)
        Assert.assertEquals(true, ownerProductAddViewModel.validateImageUri)
        Assert.assertEquals(false, ownerProductAddViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductAddViewModel.validated)
    }

    @Test
    fun `ownerProductAdd - field validation - negative - invalid quantity`() {
        ownerProductAddViewModel.productName = "test"
        ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
        ownerProductAddViewModel.quantity = "a10"

        ownerProductAddViewModel.validateData()

        Assert.assertEquals(true, ownerProductAddViewModel.validateProductName)
        Assert.assertEquals(true, ownerProductAddViewModel.validateImageUri)
        Assert.assertEquals(false, ownerProductAddViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductAddViewModel.validated)
    }

    @Test
    fun `ownerProductAdd - flow - save product`()  {
        runTest {
            ownerProductAddViewModel.productName = "test"
            ownerProductAddViewModel.imageUri =  "http://stackoverflow.com".toUri()
            ownerProductAddViewModel.quantity = "10"

            ownerProductAddViewModel.mapToProduct()

            Mockito.`when`(productRepository.addProduct(ownerProductAddViewModel.saveProduct, ownerProductAddViewModel.imageUri))
                .thenReturn(Result.Success(true))

            ownerProductAddViewModel.addProduct()

            Assert.assertEquals("SUCCESS", ownerProductAddViewModel.addProductResult)
        }
    }

}