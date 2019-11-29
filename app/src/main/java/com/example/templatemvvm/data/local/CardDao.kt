package com.example.templatemvvm.data.local

import androidx.room.*
import com.example.templatemvvm.data.models.CardData

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getCards(): List<CardData>

    @Query("SELECT * FROM cards WHERE id=:cardId")
    fun get(cardId: Int): CardData?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(cardData: CardData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCards(cards: List<CardData>)

    @Delete
    fun removeCard(cardData: CardData)

    @Query("DELETE FROM cards")
    fun clear()
}