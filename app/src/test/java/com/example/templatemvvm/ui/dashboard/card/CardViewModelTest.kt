package com.example.templatemvvm.ui.dashboard.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class CardViewModelTest {

    private lateinit var cardRepository: CardRepository
    private lateinit var cardViewModel: CardViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        cardRepository = mock(CardRepository::class.java)
        cardViewModel = CardViewModel(cardRepository)
    }

    @Test
    fun initTest() {
        assert(cardViewModel.viewState.value == CardsViewState())
    }

    @Test
    fun getCardsOnSuccessWhenFullList() {
        val list = TestsUtils.generateCardsEntitysList()
        whenever(cardRepository.getAll()).thenReturn(Observable.just(list))
        val cards = list.map {
            CardVO(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                image = it.image,
                quantity = it.quantity
            )
        }
        cardViewModel.getCards()
        testScheduler.triggerActions()
        val result = CardsViewState(showLoading = false, cards = cards)
        assert(cardViewModel.viewState.value == result)
        assert(cardViewModel.errorState.value == null)
    }



    @Test
    fun getProductsOnSuccessAndListIsEmpty() {
        whenever(cardRepository.getAll()).thenReturn(Observable.just(emptyList()))
        cardViewModel.getCards()
        testScheduler.triggerActions()
        assert(cardViewModel.viewState.value == null)
        assert(cardViewModel.errorState.value == null)
    }

    @Test
    fun getProductsOnFailed() {
        whenever(cardRepository.getAll()).thenReturn(Observable.error(Throwable("error")))
        cardViewModel.getCards()
        testScheduler.triggerActions()
        assert(cardViewModel.viewState.value == cardViewModel.viewState.value?.copy(showLoading = false))
        AssertionError(cardViewModel.errorState.value == Throwable("error"))
    }
}