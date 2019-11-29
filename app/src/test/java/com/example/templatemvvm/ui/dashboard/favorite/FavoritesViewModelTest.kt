package com.example.templatemvvm.ui.dashboard.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.repository.FavoritesRepository
import com.example.templatemvvm.ui.dashboard.favorites.FavoritesViewModel
import com.example.templatemvvm.ui.dashboard.home.ProductsViewState
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
import org.mockito.Mockito.mock

class FavoritesViewModelTest {

    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var favoritesViewModel: FavoritesViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        favoritesRepository = mock(FavoritesRepository::class.java)
        favoritesViewModel = FavoritesViewModel(favoritesRepository)
    }

    @Test
    fun initTest() {
        assert(favoritesViewModel.viewState.value == ProductsViewState())
    }

    @Test
    fun getFavoritesOnSuccessWhenFullList() {
        val list = TestsUtils.generateProductEntityList()
        whenever(favoritesRepository.getAll()).thenReturn(Observable.just(list))
        val products = list.map {
            ProductVO(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                image = it.image
            )
        }
        favoritesViewModel.getFavorites()
        testScheduler.triggerActions()
        val result = ProductsViewState(showLoading = false, products = products)
        assert(favoritesViewModel.viewState.value == result)
        assert(favoritesViewModel.errorState.value == null)
    }



    @Test
    fun getFavoritesOnSuccessAndListIsEmpty() {
        whenever(favoritesRepository.getAll()).thenReturn(Observable.just(emptyList()))
        favoritesViewModel.getFavorites()
        testScheduler.triggerActions()
        assert(favoritesViewModel.viewState.value == null)
        assert(favoritesViewModel.errorState.value == null)
    }

    @Test
    fun getFavoritesOnFailed() {
        whenever(favoritesRepository.getAll()).thenReturn(Observable.error(Throwable("error")))
        favoritesViewModel.getFavorites()
        testScheduler.triggerActions()
        assert(favoritesViewModel.viewState.value == favoritesViewModel.viewState.value!!.copy(showLoading = false))
        AssertionError(favoritesViewModel.errorState.value == Throwable("error"))
    }
}