package com.example.templatemvvm.data.respository

import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.local.CardDao
import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.domain.models.CardEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CardRepositoryTest {

    private lateinit var cardDao: CardDao
    private lateinit var cardRepository: CardRepository

    @Before
    fun before() {
        cardDao = mock(CardDao::class.java)
        cardRepository = CardRepository(cardDao)
    }

    @Test
    fun clear() {
        val testObservable = cardRepository.clear().test()
        verify(cardDao).clear()
        testObservable.assertComplete()
    }

    @Test
    fun saveCard() {
        val cardEntity =
            CardEntity(
                id = 1,
                name = "name1",
                price = 1.0,
                description = "description1",
                image = "image1",
                quantity = 1
            )
        cardRepository.clear()
        val testObserver = cardRepository.save(cardEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun remove() {
        val cardEntity =
            CardEntity(
                id = 1,
                name = "name1",
                price = 1.0,
                description = "description1",
                image = "image1",
                quantity = 1
            )
        cardRepository.clear()
        val testObserver = cardRepository.remove(cardEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveAll(){
        val list = TestsUtils.generateCardsEntitysList()
        val testObserver = cardRepository.saveAll(list).test()
        testObserver.assertComplete()
    }

    @Test
    fun getWhenProductDataIsNotNull() {
        val cardData =
            CardData(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        whenever(cardDao.get(1)).thenReturn(cardData)
        val cardEntity =
            CardEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = cardRepository.get(cardEntity.id).test()
        verify(cardDao).get(cardEntity.id)
        testObserver.assertComplete().assertValue { product-> product.value == cardEntity }
    }

    @Test
    fun getWhenProductDataIsNull() {
        whenever(cardDao.get(1)).thenReturn(null)
        val productEntity =
            CardEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = cardRepository.get(productEntity.id).test()
        verify(cardDao).get(productEntity.id)

        testObserver.assertComplete().assertValue { it.value == null}
    }

    @Test
    fun getAll() {
        val list = TestsUtils.generateCardsEntitysList()
        val testObserver = cardRepository.saveAll(list).test()
        testObserver.assertComplete()
        val testObservers = cardRepository.getAll().test()
        verify(cardDao).getCards()
        testObservers.assertComplete()
    }

    @Test
    fun isEmpty() {
        whenever(cardDao.getCards()).thenReturn(emptyList())
        val testObserver = cardRepository.isEmpty().test()
        verify(cardDao).getCards()
        testObserver.assertComplete()
    }

}