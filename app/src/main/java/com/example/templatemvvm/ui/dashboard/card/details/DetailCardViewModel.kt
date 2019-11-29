package com.example.templatemvvm.ui.dashboard.card.details

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailCardViewModel @Inject
constructor(private val cardRepository: CardRepository) : BaseViewModel() {

    var viewState: MutableLiveData<CardVO> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()


    fun getDetailCard(cardId: Int) {
        addDisposable(
            cardRepository.get(cardId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap {
                    if (it.value != null) {
                        Observable.just(
                            CardVO(
                                id = it.value.id,
                                name = it.value.name,
                                price = it.value.price,
                                description = it.value.description,
                                image = it.value.image,
                                quantity = it.value.quantity
                            )
                        )
                    } else Observable.error(Throwable("value is null"))

                }
                .subscribe({
                    Timber.d("getDetailCard onSuccess $it")
                    viewState.value = it
                }, {
                    Timber.d("getDetailCard onfailed ${it.message}")
                    errorState.value = it
                })
        )
    }
}