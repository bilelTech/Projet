package com.example.templatemvvm.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.repository.ScreensRepositoryImpl
import com.example.templatemvvm.utils.TestsUtils
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class OnBoardingViewModelTest {

    private lateinit var screensRepositoryImpl: ScreensRepositoryImpl
    private lateinit var onBoardingViewModel: OnBoardingViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        screensRepositoryImpl = Mockito.mock(ScreensRepositoryImpl::class.java)
        onBoardingViewModel = OnBoardingViewModel(screensRepositoryImpl)
    }

    @Test
    fun getScreenOnSuccessAndListIsFull() {
        val list = TestsUtils.generateOnBoardingEntityList()
        whenever(screensRepositoryImpl.getScreens()).thenReturn(Single.just(list))
        onBoardingViewModel.getScreens()
        testScheduler.triggerActions()
        val result = Screens(screens = list.map {
            ScreenItem(
                title = it.title,
                description = it.description,
                screenImg = it.screenImg
            )
        })
        assert(onBoardingViewModel.mutableLiveData.value == result)
    }

    @Test
    fun getProductsOnFailed() {
        whenever(screensRepositoryImpl.getScreens()).thenReturn(Single.error(Throwable("error")))
        onBoardingViewModel.getScreens()
        testScheduler.triggerActions()
        AssertionError(onBoardingViewModel.errorState.value == Throwable("error"))
    }
}