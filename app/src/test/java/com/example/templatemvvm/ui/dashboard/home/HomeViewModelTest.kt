package com.example.templatemvvm.ui.dashboard.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    private lateinit var productsRepositoryImpl: ProductsRepositoryImpl
    private lateinit var homeViewModel: HomeViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        productsRepositoryImpl = mock(ProductsRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(productsRepositoryImpl)
    }


    @Test
    fun initTest() {
        assert(homeViewModel.viewState.value == ProductsViewState())
    }

    @Test
    fun getProductsOnSuccessAndListIsFull() {
        val list = TestsUtils.generateProductEntityList()
        whenever(productsRepositoryImpl.getProducts()).thenReturn(Observable.just(list))
        val products = list.map {
            ProductVO(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                image = it.image
            )
        }
        homeViewModel.getProducts()
        testScheduler.triggerActions()
        val result = ProductsViewState(showLoading = false, products = products)
        assert(homeViewModel.viewState.value == result)
        assert(homeViewModel.errorState.value == null)
    }

    @Test
    fun getProductsOnSuccessAndListIsEmpty() {
        whenever(productsRepositoryImpl.getProducts()).thenReturn(Observable.just(emptyList()))
        homeViewModel.getProducts()
        testScheduler.triggerActions()
        assert(homeViewModel.viewState.value == null)
        assert(homeViewModel.errorState.value == null)
    }

    @Test
    fun getProductsOnFailed() {
        whenever(productsRepositoryImpl.getProducts()).thenReturn(Observable.error(Throwable("error")))
        homeViewModel.getProducts()
        testScheduler.triggerActions()
        assert(homeViewModel.viewState.value == homeViewModel.viewState.value?.copy(showLoading = false))
        AssertionError(homeViewModel.errorState.value == Throwable("error"))
    }
}