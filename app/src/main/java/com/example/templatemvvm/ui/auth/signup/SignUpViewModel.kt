package com.example.templatemvvm.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.extensions.isEmailValid
import com.example.templatemvvm.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel @Inject
constructor(private val userRepository: UserRepositoryImpl) : BaseViewModel() {

    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var btnSelected: MutableLiveData<Boolean> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        showProgress.value = false
    }

    fun onChangeText(name: String, email: String, password: String, zipCode: String, country: String, city: String) {
        btnSelected.value = name.isNotEmpty() && email.isEmailValid() &&
                password.isNotEmpty() && zipCode.isNotEmpty() &&
                country.isNotEmpty() && city.isNotEmpty()
    }

    fun signUp(userV: User) {
        showProgress.value = true
        addDisposable(
            userRepository.signUp(
                User(
                    userV.idUser,
                    userV.name,
                    userV.email,
                    userV.password,
                    userV.zipCode,
                    userV.country,
                    userV.city,
                    userV.apiKey
                )
            ).subscribe({
                Timber.d("signUp onSuccess $it")
                user.value = it
                showProgress.value = false
            }, {
                Timber.d("signUp onFailed $it")
                errorState.value = it
                showProgress.value = false
            })
        )
    }
}