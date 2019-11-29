package com.example.templatemvvm.data.respository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.local.ScreenDao
import com.example.templatemvvm.data.models.Screens
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.ScreensRepositoryImpl
import com.example.templatemvvm.utils.TestsUtils
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class ScreensRepositoryImplTest {

    private lateinit var screenDao: ScreenDao
    private lateinit var remoteService: RemoteService
    private lateinit var screensRepositoryImpl: ScreensRepositoryImpl
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        screenDao = mock(ScreenDao::class.java)
        remoteService = mock(RemoteService::class.java)
        screensRepositoryImpl = ScreensRepositoryImpl(screenDao,remoteService)
    }

    @Test
    fun getScreensWhenCacheIsEmpty() {
        val list = TestsUtils.generateOnBoardingEntityList()
        whenever(remoteService.getScreens()).thenReturn(Single.just(Screens(list)))
        whenever(screenDao.getScreens()).thenReturn(emptyList())
        screensRepositoryImpl.getScreens()
        testScheduler.triggerActions()
        verify(remoteService).getScreens()
    }

    @Test
    fun getScreensWhenCacheIsFull() {
        val list = TestsUtils.generateOnBoardingEntityList()
        whenever(screenDao.getScreens()).thenReturn(list)
        screensRepositoryImpl.getScreens()
        testScheduler.triggerActions()
        verify(screenDao).getScreens()

    }
}