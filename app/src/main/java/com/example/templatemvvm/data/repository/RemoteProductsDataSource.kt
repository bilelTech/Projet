package com.example.templatemvvm.data.repository

import com.example.templatemvvm.data.local.UserDao
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductDataStore
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable

class RemoteProductsDataSource(
    private val remoteService: RemoteService,
    private val userDao: UserDao
) : ProductDataStore {
    override fun getProductById(productId: Int): Observable<Optional<ProductEntity>> {
        return remoteService.getProductDetail(productId).flatMap { detailedData ->
            Observable.just(
                Optional.of(
                    ProductEntity(
                        created_at = detailedData.created_at,
                        description = detailedData.description,
                        id = detailedData.id,
                        image = detailedData.image,
                        name = detailedData.name,
                        price = detailedData.price.toDouble(),
                        sku = detailedData.sku
                    )
                )
            )
        }
    }

    override fun getProduct(): Observable<List<ProductEntity>> {
        return remoteService.getProducts(userDao.getUser()?.apiKey ?: "")
            .map { results ->
                results.products.map {
                    ProductEntity(
                        created_at = it.created_at,
                        description = it.description,
                        id = it.id,
                        image = it.image,
                        name = it.name,
                        price = it.price.toDouble(),
                        sku = it.sku
                    )
                }
            }
    }
}