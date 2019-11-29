package com.example.templatemvvm.mocks.fake

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductsRepository
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

class FakeProductsRepository : ProductsRepository{
    override fun getProducts(): Observable<List<ProductEntity>> {
        return Observable.just(emptyList())
    }

    override fun getProduct(productId: Int): Observable<Optional<ProductEntity>> {
        return Observable.just(Optional(ProductEntity(1,"name",1.0,"description","image")))
    }
}