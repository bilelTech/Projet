package com.example.templatemvvm.ui.dashboard.home

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject
constructor(private val productsRepositoryImpl: ProductsRepositoryImpl) : BaseViewModel() {

    var viewState: MutableLiveData<ProductsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ProductsViewState()
    }

    fun getProducts() {
        addDisposable(
            productsRepositoryImpl.getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap { productsEntity ->
                    Observable.just(productsEntity.map {
                        ProductVO(
                            id = it.id,
                            name = it.name,
                            price = it.price,
                            description = it.description,
                            image = it.image
                        )
                    })
                }
                .subscribe({
                    Timber.d("getProducts onSuccess $it")
                    if (it.isEmpty()) {
                        this.viewState.value = null
                        this.errorState.value = null
                    } else {
                        val newState = this.viewState.value!!.copy(showLoading = false, products = it)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    Timber.d("getProducts onFailed ${it.message}")
                    viewState.value = viewState.value!!.copy(showLoading = false)
                    errorState.value = it
                })
        )
    }
}