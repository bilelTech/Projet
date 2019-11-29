package com.example.templatemvvm.data.repository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductsCache
import com.example.templatemvvm.domain.repository.ProductDataStore
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

class CachedProductDataStore(private val productCache: ProductsCache) : ProductDataStore {

    override fun getProductById(productId: Int): Observable<Optional<ProductEntity>> {
        return productCache.get(productId)
    }

    override fun getProduct(): Observable<List<ProductEntity>> {
        return productCache.getAll()
    }
}