package com.example.templatemvvm.data.respository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.RemoteProductsDataSource
import com.example.templatemvvm.domain.models.ProductEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class RemoteProductsDataSourceTest {

    private lateinit var remoteService: RemoteService
    private lateinit var remoteProductsDataSource: RemoteProductsDataSource
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        remoteService = mock(RemoteService::class.java)
        remoteProductsDataSource = RemoteProductsDataSource(remoteService)
    }

    @Test
    fun getProductByIdTest() {
        val productData = TestsUtils.generateProductData()
        whenever(remoteService.getProductDetail(1)).thenReturn(Observable.just(productData))
        testScheduler.triggerActions()
        remoteProductsDataSource.getProductById(1).test()
            //.assertValue { item -> item.value?.name == "Product1" }
            .assertValue { item ->
                item.value == ProductEntity(
                    id = productData.id,
                    name = productData.name,
                    price = productData.price,
                    description = productData.description,
                    image = productData.image
                )
            }
            .assertComplete()
        verify(remoteService).getProductDetail(1)
    }

    @Test
    fun getProductsTest() {
        whenever(remoteService.getProducts()).thenReturn(Observable.just(ProductListResult(TestsUtils.generateProductDataList())))
        remoteProductsDataSource.getProduct().test()
            .assertValue { list -> list.size == 5 && list[0].name == "Product0" }
            .assertComplete()
    }
}