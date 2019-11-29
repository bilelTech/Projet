package com.example.templatemvvm.ui.dashboard.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.ui.dashboard.profile.models.UserVO
import com.nhaarman.mockitokotlin2.verify
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

class ProfileViewModelTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private lateinit var profileViewModel: ProfileViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        userRepositoryImpl = mock(UserRepositoryImpl::class.java)
        profileViewModel = ProfileViewModel(userRepositoryImpl)
    }

    @Test
    fun getUserSuccessResponse() {
        val user = User(
            idUser = 12,
            email = "test@gmail.com",
            password = "123465799",
            username = "testest",
            photourl = "photourl"
        )
        whenever(userRepositoryImpl.getUser()).thenReturn(
            Single.just(user)
        )
        profileViewModel.getUser()
        testScheduler.triggerActions()
        assert(
            profileViewModel.viewState.value == UserVO(
                idUser = user.idUser,
                email = user.email,
                password = user.password,
                username = user.username,
                photourl = user.photourl
            )
        )
    }

    @Test
    fun getUserOnFailedResponse() {
        whenever(userRepositoryImpl.getUser()).thenReturn(
            Single.error(Throwable("error"))
        )

        profileViewModel.getUser()
        testScheduler.triggerActions()
        AssertionError(profileViewModel.errorState.value == Throwable("error"))
    }

    @Test
    fun onClickMenuTest() {
        profileViewModel.onClickMenu()
        verify(userRepositoryImpl).logout()
        assert(profileViewModel.logoutState.value == true)
    }
}