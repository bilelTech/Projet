package com.example.templatemvvm.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.utils.TestsUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardDaoTest : DBTest() {

    @Test
    fun saveAndGetCardTest() {
        db.cardDao().clear()
        val list = TestsUtils.generateCardsDataList()
        db.cardDao().saveAllCards(list)
        val listDb = db.cardDao().getCards()
        Assert.assertTrue(list.size == listDb.size)
    }

    @Test
    fun getCardTest() {
        db.cardDao().clear()
        val card = CardData(1,"name1",10.0,"description1","image1",1)
        db.cardDao().saveCard(card)
        val cardDb = db.cardDao().get(1)
        Assert.assertTrue(card.name == cardDb?.name)
    }

    @Test
    fun removeCardTest() {
        val card = CardData(1,"name1",10.0,"description","image",1)
        db.cardDao().saveCard(card)
        db.cardDao().removeCard(card)
        val cardDb = db.cardDao().get(1)
        Assert.assertNull(cardDb)
    }
}