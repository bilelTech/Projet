package com.example.templatemvvm.ui.dashboard.card.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.domain.utils.Optional
import com.example.templatemvvm.ui.dashboard.card.details.DetailCardViewModel
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailCardViewModelTest {
    private lateinit var cardRepository: CardRepository
    private lateinit var detailCardViewModel: DetailCardViewModel
    private val testScheduler = TestScheduler()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        cardRepository = mock(CardRepository::class.java)
        detailCardViewModel = DetailCardViewModel(cardRepository)
    }

    @Test
    fun getDetailCardOnSuccess() {
        val cardEntity = CardEntity(id=1,name = "name",price = 1.0,description = "description",image = "image")
        val cardVo = CardVO(id = cardEntity.id,name = cardEntity.name,price = cardEntity.price,description = cardEntity.description,image = cardEntity.image,quantity = cardEntity.quantity)
        whenever(cardRepository.get(cardId = 1)).thenReturn(Observable.just(Optional.of(cardEntity)))
        detailCardViewModel.getDetailCard(1)
        testScheduler.triggerActions()
        assert(detailCardViewModel.viewState.value == cardVo)
    }

    @Test
    fun getDetailCardOnError() {
        whenever(cardRepository.get(cardId = 1)).thenReturn(Observable.error(Throwable("error")))
        detailCardViewModel.getDetailCard(1)
        testScheduler.triggerActions()
        AssertionError(detailCardViewModel.errorState.value == Throwable("error"))
    }

    @Test
    fun getCardCardWhenValueIsNull() {
        whenever(cardRepository.get(cardId = 1)).thenReturn(Observable.just(Optional.of(null)))
        detailCardViewModel.getDetailCard(1)
        testScheduler.triggerActions()
        AssertionError(detailCardViewModel.errorState.value == Throwable("value is null"))
    }

}