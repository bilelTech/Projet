package com.example.templatemvvm.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.ui.base.BaseViewModel
import io.reactivex.Single
import javax.inject.Inject

class SplashViewModel @Inject
constructor(private val userRepository: UserRepositoryImpl) : BaseViewModel() {
    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()

    fun checkUser() {
        addDisposable(getUser().subscribe({
            mutableLiveData.postValue(SplashState.MainActivity())
        }, {
            mutableLiveData.postValue(SplashState.OnBoardIngActivity())
        }))
    }

    private fun getUser(): Single<User> {
        return userRepository.getUser()
    }

    sealed class SplashState {
        class MainActivity : SplashState()
        class OnBoardIngActivity : SplashState()
    }
}