package com.example.templatemvvm.data.respository

import com.example.templatemvvm.data.local.UserDao
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler


class UserRepositoryImplTest {
    private lateinit var userDao: UserDao
    private lateinit var remoteService: RemoteService
    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private val testScheduler = TestScheduler()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        remoteService = Mockito.mock(RemoteService::class.java)
        userDao = Mockito.mock(UserDao::class.java)
        userRepositoryImpl = UserRepositoryImpl(userDao, remoteService)
    }

    @Test
    fun loadUser() {
        userRepositoryImpl.loadUser("12")
        verify(remoteService).getUser("12")
    }

    @Test
    fun signIn() {
        val user = User(
            idUser = 12,
            email = "test@gmail.com",
            password = "1234",
            username = "username",
            photourl = "photourl"
        )
        Mockito.`when`(remoteService.signIn(email = "test@gmail.com", password = "1234")).thenReturn(Single.just(user))
       val test = userRepositoryImpl.signIn(user).test()
        verify(remoteService).signIn(email = "test@gmail.com", password = "1234")
        testScheduler.triggerActions()
        test.assertComplete().assertValue { result-> result.email == "test@gmail.com"}
        verify(userDao).insertUser(user)
    }

    @Test
    fun signUp() {
        val user = User(
            idUser = 12,
            email = "test@gmail.com",
            password = "1234",
            username = "username",
            photourl = "photourl"
        )
        Mockito.`when`(remoteService.signUp(user)).thenReturn(Single.just(user))
        val test = userRepositoryImpl.signUp(user).test()
        verify(remoteService).signUp(user=user)
        testScheduler.triggerActions()
        test.assertComplete().assertValue { result-> result.email == "test@gmail.com"}
        verify(userDao).insertUser(user)
    }

    @Test
    fun getUserWhenNull() {
        Mockito.`when`(userDao.getUser()).thenReturn(null)
        userRepositoryImpl.getUser().test().assertErrorMessage("user is empty")
    }

    @Test
    fun getUserWhenIsNotEmpty() {
        val user = User(
            idUser = 12,
            email = "test@gmail.com",
            password = "1234",
            username = "username",
            photourl = "photourl"
        )
        Mockito.`when`(userDao.getUser()).thenReturn(user)
        userRepositoryImpl.getUser().test().onSuccess(user)
    }

    @Test
    fun logout() {
        userRepositoryImpl.logout().test().assertComplete()
        verify(userDao).clear()
    }
}