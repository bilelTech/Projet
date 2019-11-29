package com.example.templatemvvm.ui.dashboard.card

import androidx.lifecycle.MutableLiveData
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.extensions.SingleLiveEvent
import com.example.templatemvvm.ui.base.BaseViewModel
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CardViewModel @Inject
constructor(private val cardRepository: CardRepository) : BaseViewModel() {

    var viewState: MutableLiveData<CardsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = CardsViewState()
    }

    fun getCards() {
        addDisposable(cardRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap { cardsEntity ->
                Observable.just(cardsEntity.map {
                    CardVO(
                        id = it.id,
                        name = it.name,
                        price = it.price,
                        description = it.description,
                        image = it.image,
                        quantity = it.quantity
                    )
                })
            }.subscribe({
                Timber.d("getCards onSuccess $it")
                if (it.isEmpty()) {
                    this.viewState.value = null
                    this.errorState.value = null
                } else {
                    val newState = this.viewState.value!!.copy(showLoading = false, cards = it)
                    this.viewState.value = newState
                    this.errorState.value = null
                }
            }, {
                Timber.d("getCards onFailed ${it.message}")
                viewState.value = viewState.value!!.copy(showLoading = false)
                errorState.value = it
            })
        )
    }
}