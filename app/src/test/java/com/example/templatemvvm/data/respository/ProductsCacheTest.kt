package com.example.templatemvvm.data.respository

import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductsCache
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable
import java.util.LinkedHashMap

class ProductsCacheTest : ProductsCache {

    private val products: LinkedHashMap<Int, ProductEntity> = LinkedHashMap()

    override fun clear() {
        products.clear()
    }

    override fun save(productEntity: ProductEntity) {
        products[productEntity.id] = productEntity
    }

    override fun remove(productEntity: ProductEntity) {
        products.remove(productEntity.id)
    }

    override fun saveAll(productsEntity: List<ProductEntity>) {
        productsEntity.forEach { this.products[it.id] = it }
    }

    override fun getAll(): Observable<List<ProductEntity>> {
        return Observable.just(products.values.toList())
    }

    override fun get(productId: Int): Observable<Optional<ProductEntity>> {
        return Observable.just(Optional.of(products[productId]))
    }

    override fun isEmpty(): Boolean {
        return products.isEmpty()
    }

}