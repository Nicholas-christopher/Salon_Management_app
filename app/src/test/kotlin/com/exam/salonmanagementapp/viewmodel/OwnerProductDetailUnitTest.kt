package com.exam.salonmanagementapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.net.toUri
import com.exam.salonmanagementapp.data.Product
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
class OwnerProductDetailUnitTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var ownerProductDetailViewModel: OwnerProductDetailViewModel

    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepository = Mockito.mock(ProductRepository::class.java)
        ownerProductDetailViewModel = OwnerProductDetailViewModel(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `ownerProductDetail - field validation - positive`() {
        ownerProductDetailViewModel.quantity = "10"

        ownerProductDetailViewModel.validateData()

        Assert.assertEquals(true, ownerProductDetailViewModel.validateQuantity)
        Assert.assertEquals(true, ownerProductDetailViewModel.validated)
    }

    @Test
    fun `ownerProductDetail - field validation - negative - missing quantity`() {
        //ownerProductDetailViewModel.quantity = "10"

        ownerProductDetailViewModel.validateData()

        Assert.assertEquals(false, ownerProductDetailViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductDetailViewModel.validated)
    }

    @Test
    fun `ownerProductDetail - field validation - negative - invalid quantity`() {
        ownerProductDetailViewModel.quantity = "10a"

        ownerProductDetailViewModel.validateData()

        Assert.assertEquals(false, ownerProductDetailViewModel.validateQuantity)
        Assert.assertEquals(false, ownerProductDetailViewModel.validated)
    }

    @Test
    fun `ownerProductDetail - flow - get product`()  {
        runTest {
            val product = Product()
            Mockito.`when`(productRepository.getProduct("1"))
                .thenReturn(Result.Success(product))

            ownerProductDetailViewModel.getProduct("1")

            Assert.assertEquals("SUCCESS", ownerProductDetailViewModel.productResult)
            Assert.assertEquals(product, ownerProductDetailViewModel.product)
        }
    }

    @Test
    fun `ownerProductDetail - flow - restock product`()  {
        runTest {
            ownerProductDetailViewModel.quantity = "10"
            val product = Product()

            Mockito.`when`(productRepository.getProduct("1"))
                .thenReturn(Result.Success(product))

            ownerProductDetailViewModel.getProduct("1")
            ownerProductDetailViewModel.mapToProduct(true)

            Mockito.`when`(productRepository.saveProduct(ownerProductDetailViewModel.saveProduct))
                .thenReturn(Result.Success(true))

            ownerProductDetailViewModel.addProduct()

            Assert.assertEquals("SUCCESS", ownerProductDetailViewModel.saveProductResult)
        }
    }

    @Test
    fun `ownerProductDetail - flow - consume product`()  {
        runTest {
            ownerProductDetailViewModel.quantity = "10"
            val product = Product(quantity = 100)

            Mockito.`when`(productRepository.getProduct("1"))
                .thenReturn(Result.Success(product))

            ownerProductDetailViewModel.getProduct("1")
            ownerProductDetailViewModel.mapToProduct(false)

            Mockito.`when`(productRepository.saveProduct(ownerProductDetailViewModel.saveProduct))
                .thenReturn(Result.Success(true))

            ownerProductDetailViewModel.useProduct()

            Assert.assertEquals("SUCCESS", ownerProductDetailViewModel.saveProductResult)
        }
    }

    @Test
    fun `ownerProductDetail - flow - delete product`()  {
        runTest {
            val product = Product()

            Mockito.`when`(productRepository.getProduct("1"))
                .thenReturn(Result.Success(product))

            ownerProductDetailViewModel.getProduct("1")

            Mockito.`when`(productRepository.deleteProduct(ownerProductDetailViewModel.product))
                .thenReturn(Result.Success(true))

            ownerProductDetailViewModel.deleteProduct()

            Assert.assertEquals("SUCCESS", ownerProductDetailViewModel.saveProductResult)
        }
    }
}