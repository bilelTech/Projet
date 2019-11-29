package com.example.templatemvvm.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class SplashViewModelTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private lateinit var splashViewModel: SplashViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        userRepositoryImpl = mock(UserRepositoryImpl::class.java)
        splashViewModel = SplashViewModel(userRepositoryImpl)
    }

    @Test
    fun checkWhenUserIsConnected() {
        val user = User(1, "test@gmail.com", "123456789", "username", "photourl")
        whenever(userRepositoryImpl.getUser()).thenReturn(Single.just(user))
        splashViewModel.checkUser()
        testScheduler.triggerActions()
        assert(splashViewModel.liveData.value is SplashViewModel.SplashState.MainActivity)
    }

    @Test
    fun checkWhenUserIsNotConnected() {
        whenever(userRepositoryImpl.getUser()).thenReturn(Single.error(Throwable("user not exist")))
        splashViewModel.checkUser()
        testScheduler.triggerActions()
        assert(splashViewModel.liveData.value is SplashViewModel.SplashState.OnBoardIngActivity)
    }
}