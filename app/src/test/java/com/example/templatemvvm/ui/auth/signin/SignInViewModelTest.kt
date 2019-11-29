package com.example.templatemvvm.ui.auth.signin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SignInViewModelTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private lateinit var signInViewModel: SignInViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        userRepositoryImpl = Mockito.mock(UserRepositoryImpl::class.java)
        signInViewModel = SignInViewModel(userRepositoryImpl)
    }

    @Test
    fun initTest() {
        assert(signInViewModel.btnSelected.value == false)
    }

    /***begin onEmailChanged use case***/
    @Test
    fun onEmailWhenEmailIsEmptyChangedDisableButton() {
        signInViewModel.email.value = ""
        signInViewModel.password.value = "12345677"
        signInViewModel.onEmailChanged("")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onEmailWhenPasswordIsLessLenghtChangedDisableButton() {
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "12345"
        signInViewModel.onEmailChanged("")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onEmailWhenAllFiledIsEmptyLenghtChangedDisableButton() {
        signInViewModel.email.value = ""
        signInViewModel.password.value = ""
        signInViewModel.onEmailChanged("")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onEmailWhenAllFiledIsFullChangedEnableButton() {
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "233545789"
        signInViewModel.onEmailChanged("test@gmail.com")
        assert(signInViewModel.btnSelected.value == true)
    }

    @Test
    fun onEmailWhenPasswordIsNullChangedDisableButton() {
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = null
        signInViewModel.onEmailChanged("test@gmail.com")
        assert(signInViewModel.btnSelected.value == false)
    }
    /***end onEmailChanged use case***/


    /***begin onPasswordChanged use case***/
    @Test
    fun onPasswordWhenEmailIsEmptyChangedDisableButton() {
        signInViewModel.email.value = ""
        signInViewModel.password.value = "12345677"
        signInViewModel.onPasswordChanged("12345677")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenPasswordIsLessLenghtChangedDisableButton() {
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "12345"
        signInViewModel.onPasswordChanged("12345")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenAllFiledIsEmptyLenghtChangedDisableButton() {
        signInViewModel.email.value = ""
        signInViewModel.password.value = ""
        signInViewModel.onPasswordChanged("")
        assert(signInViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenAllFiledIsFullChangedEnableButton() {
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "233545789"
        signInViewModel.onPasswordChanged("test@gmail.com")
        assert(signInViewModel.btnSelected.value == true)
    }

    @Test
    fun onPasswordWhenEmailIsNullChangedDisableButton() {
        signInViewModel.email.value = null
        signInViewModel.password.value = "12345245666"
        signInViewModel.onPasswordChanged("12345245666")
        assert(signInViewModel.btnSelected.value == false)
    }
    /***end onPasswordChanged use case***/

    /***begin onSignIn use case***/
    @Test
    fun onSignInSuccess() {
        val user = User(idUser = 12,email = "test@gmail.com",password = "123454783833",username = "username",photourl = "photourl")
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "123454I83833"
        Mockito.`when`(userRepositoryImpl.signIn(user)).thenReturn(Single.just(user))
        val test =userRepositoryImpl.signIn(user).test()
        test.assertComplete().assertValue { result->
            result.email == "test@gmail.com"
        }
        signInViewModel.signIn(user)
        testScheduler.triggerActions()
        Assert.assertTrue(signInViewModel.user.value === user)
    }
    @Test
    fun onSignInFailed() {
        val user = User(idUser = 12,email = "test@gmail.com",password = "123454783833",username = "username",photourl = "photourl")
        signInViewModel.email.value = "test@gmail.com"
        signInViewModel.password.value = "123454I83833"
        Mockito.`when`(userRepositoryImpl.signIn(user)).thenReturn(Single.error(Throwable("error")))
        signInViewModel.signIn(user)
        testScheduler.triggerActions()
        AssertionError(signInViewModel.errorState.value == Throwable("error"))
    }
    /***end onSignIn use case***/

}