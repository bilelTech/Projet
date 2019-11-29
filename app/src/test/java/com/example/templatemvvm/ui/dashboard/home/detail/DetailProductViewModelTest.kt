package com.example.templatemvvm.ui.dashboard.home.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductViewModel
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailProductViewModelTest {

    private lateinit var productsRepositoryImpl: ProductsRepositoryImpl
    private lateinit var detailProductViewModel: DetailProductViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        productsRepositoryImpl = Mockito.mock(ProductsRepositoryImpl::class.java)
        detailProductViewModel = DetailProductViewModel(productsRepositoryImpl)
    }


    @Test
    fun getDetailCardOnSuccess() {
        val productEntity = ProductEntity(id=1,name = "name",price = 1.0,description = "description",image = "image")
        val productVo = ProductVO(id = productEntity.id,name = productEntity.name,price = productEntity.price,description = productEntity.description,image = productEntity.image)
        whenever(productsRepositoryImpl.getProduct(productId = 1)).thenReturn(Observable.just(Optional.of(productEntity)))
        detailProductViewModel.getDetailProduct(productId = 1)
        testScheduler.triggerActions()
        assert(detailProductViewModel.viewState.value == productVo)
    }

    @Test
    fun getDetailCardOnError() {
        whenever(productsRepositoryImpl.getProduct(productId = 1)).thenReturn(Observable.error(Throwable("error")))
        detailProductViewModel.getDetailProduct(1)
        testScheduler.triggerActions()
        AssertionError(detailProductViewModel.errorState.value == Throwable("error"))
    }

    @Test
    fun getCardCardWhenValueIsNull() {
        whenever(productsRepositoryImpl.getProduct(productId = 1)).thenReturn(Observable.just(Optional.of(null)))
        detailProductViewModel.getDetailProduct(productId = 1)
        testScheduler.triggerActions()
        AssertionError(detailProductViewModel.errorState.value == Throwable("value is null"))
    }
}