package com.example.templatemvvm.data.respository

import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.utils.DomainTestUtils.Companion.generateProductsEntityList
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ProductsRepositoryImplTest {

    private lateinit var productsRepositoryImpl: ProductsRepositoryImpl
    private lateinit var remoteService: RemoteService
    private lateinit var productsCache: ProductsCacheTest

    @Before
    fun before() {
        remoteService = mock(RemoteService::class.java)
        productsCache = ProductsCacheTest()
        productsRepositoryImpl = ProductsRepositoryImpl(remoteService, productsCache)
    }

    @Test
    fun testWhenCacheIsNotEmptyGetProductsReturnCachedProducts() {
        productsCache.saveAll(generateProductsEntityList())
        productsRepositoryImpl.getProducts().test()
            .assertComplete()
            .assertValue { products -> products.size == 5 }

        verifyZeroInteractions(remoteService)
    }

    @Test
    fun testWhenCacheIsNotEmptyGetProductsReturnProductFromApi() {
        val productListResult = ProductListResult()
        productListResult.results = TestsUtils.generateProductDataList()
        `when`(remoteService.getProducts()).thenReturn(Observable.just(productListResult))
        productsRepositoryImpl.getProducts().test()
            .assertComplete()
            .assertValue { products -> products.size == 5 }
    }

    @Test
    fun testGetProductByIdReturnedApiProduct() {
        val detailData = TestsUtils.getMockedProductData(
            id = 100,
            name = "name",
            price = 0.0,
            description = "description",
            image = "image"
        )
        `when`(remoteService.getProductDetail(100)).thenReturn(Observable.just(detailData))
        productsRepositoryImpl.getProduct(100).test()
            .assertComplete()
            .assertValue {
                it.hasValue() && it.value == ProductEntity(
                    id = detailData.id,
                    name = detailData.name,
                    price = detailData.price,
                    description = detailData.description,
                    image = detailData.image
                )
            }
    }
}