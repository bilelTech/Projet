package com.example.templatemvvm.ui.auth.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class SignUpViewModelTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private lateinit var signUpViewModel: SignUpViewModel
    private val testScheduler = TestScheduler()
    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        userRepositoryImpl = mock(UserRepositoryImpl::class.java)
        signUpViewModel = SignUpViewModel(userRepositoryImpl)
    }

    /**
     * username empty
     * email full and password full
     */
    @Test
    fun onEmailWhenUserNameIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "1234587576565"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }


    /***** use case onEmailChanged*****/

    /***
     * all filed is empty
     */
    @Test
    fun onEmailWhenAllEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * email is not empty
     * username and password is empty
     */
    @Test
    fun onEmailWhenPasswordAndUsernameIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onEmailWhenEmailIsNullIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = null
        signUpViewModel.password.value = ""
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * email is not empty
     * username and password is empty
     */
    @Test
    fun onEmailWhenPasswordIsNotEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "biuegffguzeiu"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * password is not empty
     * username and email is empty
     */
    @Test
    fun onEmailWhenEmailAndUsernameIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "12345677654"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }


    /**
     * username is not empty
     * username and email is empty
     */
    @Test
    fun onEmailWhenUserNameIsNotEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = "testtest"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }


    /**
     * email and  username is not empty
     * password is empty
     */
    @Test
    fun onEmailWhenPasswordIsEmptyAndEmailAndUserNameIsNotEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = "testtest"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * username is  empty
     * email and email is not empty
     */
    @Test
    fun onEmailWhenUsernameIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = "testetsfgd"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "12345677786"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * email is  empty
     * email and username is not empty
     */
    @Test
    fun onEmailWhenEmailIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = "testtest"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "1234567786877"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * email is  empty
     * email and username is not empty
     */
    @Test
    fun onEmailAllFullSelectedEnable() {
        signUpViewModel.username.value = "testtest"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "1234567787655"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == true)
    }

    /**
     * email is  empty
     * email and username is not empty
     */
    @Test
    fun onEmailWhenUserNameIsNullSelectedEnable() {
        signUpViewModel.username.value = null
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "1234567787655"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }


    /**
     * email is  empty
     * email and username is not empty
     */
    @Test
    fun onEmailWhenPasswordIsNullSelectedEnable() {
        signUpViewModel.username.value = "test"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = null
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    /**
     * username empty
     * email full and password full
     */
    @Test
    fun onEmailWhenPasswordIsEmptyBtnSelectedDisable() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "1234556887655"
        signUpViewModel.onEmailChanged(signUpViewModel.email.value.toString())
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onEmailwhenEmailIsNull() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "1234556887655"
        signUpViewModel.onEmailChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    /** end usecase onEmailChanged **/


    /*****begin use case onEmailChanged*****/
    @Test
    fun onPasswordWhenAllFiledIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onPasswordChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenEmailIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onPasswordChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenPasswordIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "ndkjfnzjefzjhfbez"
        signUpViewModel.onPasswordChanged("ndkjfnzjefzjhfbez")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenEmailAndPasswordIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "ndkjfnzjefzjhfbez"
        signUpViewModel.onPasswordChanged("ndkjfnzjefzjhfbez")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenUserNameIsNotEmpty() {
        signUpViewModel.username.value = "testestt"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onPasswordChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenEmailAndUserNameIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onPasswordChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenPasswordAndUserNameIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "knscjnjfjd"
        signUpViewModel.onPasswordChanged("knscjnjfjd")
        assert(signUpViewModel.btnSelected.value == false)
    }


    @Test
    fun onPasswordWhenAllFullFiled() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "knscjnjfjdqskif"
        signUpViewModel.onPasswordChanged("knscjnjfjdqskif")
        assert(signUpViewModel.btnSelected.value == true)
    }
    /**
     * username is empty
     * email is not valid
     * password is lenght less than 8
     */
    @Test
    fun onPasswordWhenUserNameIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test"
        signUpViewModel.password.value = "bhjvyuvuyuf"
        signUpViewModel.onPasswordChanged("bhjvyuvuyuf")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenUserNameIsNull() {
        signUpViewModel.username.value = null
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "2245678900546"
        signUpViewModel.onPasswordChanged("2245678900546")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onPasswordWhenEmailIsNull() {
        signUpViewModel.username.value = "tesy"
        signUpViewModel.email.value = null
        signUpViewModel.password.value = "2245678900546"
        signUpViewModel.onPasswordChanged("2245678900546")
        assert(signUpViewModel.btnSelected.value == false)
    }

    /*****end use case onEmailChanged*****/

    /*****begin use case onUserNameChanged*****/
    @Test
    fun onUserNameWhenAllFiledIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenUserNameIsNotEmpty() {
        signUpViewModel.username.value = "testestt"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("testestt")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenEmailIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenEmailAndUserNameIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("nfiefeofo")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenPasswordIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "ndkjfnzjefzjhfbez"
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenPasswordAndUserNameIsNotEmpty() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = ""
        signUpViewModel.password.value = "knscjnjfjd"
        signUpViewModel.onUserNameChanged("nfiefeofo")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenEmailAndPasswordIsNotEmptyIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "ndkjfnzjefzjhfbez"
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenAllFullFiled() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "knscjnjfjdqskif"
        signUpViewModel.onUserNameChanged("nfiefeofo")
        assert(signUpViewModel.btnSelected.value == true)
    }


    @Test
    fun onUserNameWhenEmailIsNull() {
        signUpViewModel.username.value = "nfiefeofo"
        signUpViewModel.email.value = null
        signUpViewModel.password.value = "knscjnjfjdqskif"
        signUpViewModel.onUserNameChanged("nfiefeofo")
        assert(signUpViewModel.btnSelected.value == false)
    }
    @Test
    fun onUserNameWhenAllIsNull() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = null
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenEmailIsNullAndPasswordIsEmpty() {
        signUpViewModel.username.value = "dfoifehofezhof"
        signUpViewModel.email.value = null
        signUpViewModel.password.value = ""
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }

    @Test
    fun onUserNameWhenEmailIsNullAndUserNameIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = null
        signUpViewModel.password.value = "skfidfjiknfeief"
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }


    /**
     * username is empty
     * email is not valid
     * password is lenght less than 8
     */
    @Test
    fun onUserNameWhenUserNameIsEmpty() {
        signUpViewModel.username.value = ""
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "bhjvyuvuyuf"
        signUpViewModel.onUserNameChanged("")
        assert(signUpViewModel.btnSelected.value == false)
    }


    /*****end use case onUserNameChanged*****/

    /*** begin use case signup ****/

    @Test
    fun onSignUpSuccess() {
        val user = User(idUser = 12,email = "test@gmail.com",password = "123454783833",username = "username",photourl = "photourl")
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "123454I83833"
        signUpViewModel.username.value = "username"
        `when`(userRepositoryImpl.signUp(user)).thenReturn(Single.just(user))
        val test =userRepositoryImpl.signUp(user).test()
        test.assertComplete().assertValue { result->
            result.email == "test@gmail.com"
        }
        signUpViewModel.signUp(user)
        testScheduler.triggerActions()
        assertTrue(signUpViewModel.user.value === user)
    }


    @Test
    fun onSignUpFailed() {
        val user = User(idUser = 12,email = "test@gmail.com",password = "123454783833",username = "username",photourl = "photourl")
        signUpViewModel.email.value = "test@gmail.com"
        signUpViewModel.password.value = "123454I83833"
        signUpViewModel.username.value = "username"
        `when`(userRepositoryImpl.signUp(user)).thenReturn(Single.error(Throwable("error")))
        signUpViewModel.signUp(user)
        testScheduler.triggerActions()
        AssertionError(signUpViewModel.errorState.value == Throwable("error"))
    }

}