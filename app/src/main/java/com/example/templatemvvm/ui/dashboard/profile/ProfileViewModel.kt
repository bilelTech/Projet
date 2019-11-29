package com.example.templatemvvm.ui.dashboard.profile

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import com.example.templatemvvm.ui.dashboard.profile.models.UserVO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject
constructor(private val userRepositoryImpl: UserRepositoryImpl) : BaseViewModel() {
    var viewState: MutableLiveData<UserVO> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var logoutState: MutableLiveData<Boolean?> = MutableLiveData()

    fun getUser() {
        addDisposable(
            userRepositoryImpl.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.d("getUser onSuccess ${it.toString()}")
                    viewState.value = UserVO(
                        idUser = it.idUser,
                        name = it.name,
                        email = it.email,
                        password = it.password,
                        zipCode = it.zipCode,
                        country = it.country,
                        city = it.city,
                        apiKey = it.apiKey
                    )
                }, {
                    Timber.d("getUser onError ${it.message}")
                    errorState.value = it
                })
        )
    }

    fun onClickMenu() {
        userRepositoryImpl.logout()
        logoutState.value = true
    }
}