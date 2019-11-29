package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

interface ProductsRepository {
    fun getProducts(): Observable<List<ProductEntity>>
    fun getProduct(productId: Int): Observable<Optional<ProductEntity>>
}