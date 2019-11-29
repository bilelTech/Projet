package com.example.templatemvvm.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.extensions.isEmailValid
import com.example.templatemvvm.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SignInViewModel @Inject
constructor(private val userRepository: UserRepositoryImpl) : BaseViewModel() {

    var btnSelected: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        btnSelected.value = false
        showProgress.value = false
    }

    fun onChange(email: String, password: String) {
        btnSelected.value = email.isEmailValid() && password.isNotEmpty()
    }

    fun signIn(userV: User) {
        showProgress.value = true
        addDisposable(userRepository
            .signIn(
                User(
                    userV.idUser,
                    userV.name,
                    userV.email,
                    userV.password,
                    userV.zipCode,
                    userV.country,
                    userV.city,
                    ""
                )
            )
            .doAfterTerminate { showProgress.value = false }
            .subscribe({
                Timber.d("signIn onSuccess $it")
                user.value = it
                showProgress.value = false

            }, {
                Timber.d("signIn onFailed $it")
                errorState.value = it
                showProgress.value = false
            }
            ))
    }
}