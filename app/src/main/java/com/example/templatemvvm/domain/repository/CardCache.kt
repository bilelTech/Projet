package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Completable
import io.reactivex.Observable

interface CardCache {
    fun clear(): Completable
    fun save(cardEntity: CardEntity): Completable
    fun remove(cardEntity: CardEntity): Completable
    fun saveAll(cards: List<CardEntity>): Completable
    fun get(cardId: Int): Observable<Optional<CardEntity>>
    fun getAll(): Observable<List<CardEntity>>
    fun isEmpty(): Observable<Boolean>
}