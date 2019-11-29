package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

interface ProductDataStore {
    fun getProductById(productId: Int): Observable<Optional<ProductEntity>>
    fun getProduct(): Observable<List<ProductEntity>>
}