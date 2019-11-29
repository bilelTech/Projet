package com.example.templatemvvm.data.respository

import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.RemoteProductsDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RemoteProductsDataStoreTests {
    private lateinit var remoteService: RemoteService
    private lateinit var remoteProductsDataSource: RemoteProductsDataSource

    @Before
    fun before() {
        remoteService = Mockito.mock(RemoteService::class.java)
        remoteProductsDataSource = RemoteProductsDataSource(remoteService)
    }

    @Test
    fun whenRequestingGetProductsFromRemoteSourceReturnExpectedResult() {
        val products = (0..5).map {
            ProductData(
                id = it,
                name = "name$it",
                price = 1.0,
                description = "desc$it",
                image = "image$it"
            )
        }
        val productListResult = ProductListResult()
        productListResult.results = products
        Mockito.`when`(remoteService.getProducts()).thenReturn(Observable.just(productListResult))
        remoteProductsDataSource.getProduct().test()
            .assertValue { list -> list.size == 6 && list[0].name == "name0" }
            .assertComplete()
    }

    @Test
    fun whenRequestingGetProductByIdFromRemoteSourceReturnExpectedResult() {

           val product = ProductData(
                id = 1,
                name = "name1",
                price = 1.0,
                description = "desc1",
                image = "image1"
            )
        Mockito.`when`(remoteService.getProductDetail(1)).thenReturn(Observable.just(product))
        remoteProductsDataSource.getProductById(1).test()
            .assertValue { result -> result.value?.name == "name1" }
            .assertComplete()
    }

    @Test
    fun whenRequestingGetProductByIdFromRemoteSourceReturnNullResult() {
        Mockito.`when`(remoteService.getProductDetail(1)).thenReturn(Observable.error(Throwable("error")))
        remoteProductsDataSource.getProductById(1).test()
            .assertErrorMessage("error")
    }
}