package com.example.templatemvvm.data.repository

import com.example.templatemvvm.data.local.CardDao
import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.domain.repository.CardCache
import com.example.templatemvvm.domain.utils.Optional
import com.example.templatemvvm.utils.Utils
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CardRepository @Inject
constructor(private val cardDao: CardDao) : CardCache {

    override fun clear(): Completable {
        return Completable.fromAction {
            cardDao.clear()
        }
    }

    override fun save(cardEntity: CardEntity): Completable {
        return Completable.fromAction {
            cardDao.saveCard(
                CardData(
                    id = cardEntity.id,
                    name = cardEntity.name,
                    price = cardEntity.price,
                    description = cardEntity.description,
                    image = cardEntity.image,
                    quantity = cardEntity.quantity
                )
            )
        }
    }

    override fun remove(cardEntity: CardEntity): Completable {
        return Completable.fromAction {
            cardDao.removeCard(
                CardData(
                    id = cardEntity.id,
                    name = cardEntity.name,
                    price = cardEntity.price,
                    description = cardEntity.description,
                    image = cardEntity.image,
                    quantity = cardEntity.quantity
                )
            )
        }
    }

    override fun saveAll(cards: List<CardEntity>): Completable {
        return Completable.fromAction {
            cardDao.saveAllCards(cards.map {
                CardData(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    description = it.description,
                    image = it.image,
                    quantity = it.quantity
                )
            })
        }
    }

    override fun get(cardId: Int): Observable<Optional<CardEntity>> {
        return Observable.fromCallable {
            val cardData = cardDao.get(cardId)
            if (cardData != null) {
                Optional.of(
                    CardEntity(
                        id = cardData.id,
                        name = cardData.name,
                        price = cardData.price,
                        description = cardData.description,
                        image = cardData.image,
                        quantity = cardData.quantity
                    )
                )
            } else {
                Optional.empty()
            }
        }
    }

    override fun getAll(): Observable<List<CardEntity>> {
        return Observable.just(cardDao.getCards().map { Utils.mapperCardDataToEntity(it) })
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.just(cardDao.getCards().isEmpty())
    }
}