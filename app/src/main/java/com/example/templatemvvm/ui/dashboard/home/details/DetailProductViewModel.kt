package com.example.templatemvvm.ui.dashboard.home.details

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.R
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailProductViewModel @Inject
constructor(private val productsRepositoryImpl: ProductsRepositoryImpl) : BaseViewModel() {

    var viewState: MutableLiveData<ProductVO> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var photoViewState: SingleLiveEvent<List<PhotoVO>> = SingleLiveEvent()

    init {
        val list = arrayOf(
            PhotoVO(R.drawable.pexel),
            PhotoVO(R.drawable.pexel),
            PhotoVO(R.drawable.pexel),
            PhotoVO(R.drawable.pexel),
            PhotoVO(R.drawable.pexel)
        )
        photoViewState.value = list.toList()
    }

    fun getDetailProduct(productId: Int) {
        addDisposable(
            productsRepositoryImpl.getProduct(productId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap {
                    if (it.value != null) {
                        Observable.just(
                            ProductVO(
                                id = it.value.id
                                , name = it.value.name,
                                price = it.value.price,
                                description = it.value.description,
                                image = it.value.image
                            )
                        )
                    } else {
                        Observable.error(Throwable("value is null"))
                    }
                }
                .subscribe({
                    Timber.d("getDetailProduct onSuccess $it")
                    viewState.value = it
                }, {
                    Timber.d("getDetailProduct onfailed ${it.message}")
                    errorState.value = it
                })
        )
    }
}