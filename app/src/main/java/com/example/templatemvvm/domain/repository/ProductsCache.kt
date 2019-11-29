package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

interface ProductsCache {
    fun clear()
    fun save(productEntity: ProductEntity)
    fun remove(productEntity: ProductEntity)
    fun saveAll(productsEntity: List<ProductEntity>)
    fun getAll(): Observable<List<ProductEntity>>
    fun get(productId: Int): Observable<Optional<ProductEntity>>
    fun isEmpty(): Boolean
}