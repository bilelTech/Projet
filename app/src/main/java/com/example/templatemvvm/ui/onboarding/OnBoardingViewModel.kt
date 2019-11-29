package com.example.templatemvvm.ui.onboarding

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.R
import com.example.templatemvvm.data.repository.ScreensRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(private val screensRepositoryImpl: ScreensRepositoryImpl) :
    BaseViewModel() {

    val mutableLiveData = MutableLiveData<Screens>()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    fun getScreens() {
        addDisposable(
            screensRepositoryImpl.getScreens()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.d("getScreens onSuccess $it")
                    mutableLiveData.value = Screens(screens = it.map {
                        ScreenItem(
                            title = it.title,
                            description = it.description,
                            screenImg = it.screenImg
                        )
                    })
                }, {
                    Timber.d("getScreens onFailed ${it.message}")
                    errorState.value = it
                })
        )
    }


}