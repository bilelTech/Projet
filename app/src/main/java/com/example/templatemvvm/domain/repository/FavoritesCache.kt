package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface FavoritesCache {
    fun clear(): Completable
    fun save(productEntity: ProductEntity): Completable
    fun remove(productEntity: ProductEntity): Completable
    fun saveAll(productsEntity: List<ProductEntity>): Completable
    fun get(productId: Int): Observable<Optional<ProductEntity>>
    fun getAll(): Observable<List<ProductEntity>>
    fun isEmpty(): Observable<Boolean>
}