package com.example.templatemvvm.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel  : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
        get() = field
    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }


}