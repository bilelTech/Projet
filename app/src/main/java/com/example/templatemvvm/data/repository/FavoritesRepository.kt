package com.example.templatemvvm.data.repository

import com.example.templatemvvm.data.local.ProductDao
import com.example.templatemvvm.data.models.Product
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.FavoritesCache
import com.example.templatemvvm.domain.utils.Optional
import com.example.templatemvvm.utils.Utils
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavoritesRepository @Inject
constructor(private val productDao: ProductDao) : FavoritesCache {

    override fun clear(): Completable {
        return Completable.fromAction {
            productDao.clear()
        }
    }

    override fun save(productEntity: ProductEntity): Completable {
        return Completable.fromAction {
            productDao.saveProduct(
                Product(
                    created_at = productEntity.created_at,
                    description = productEntity.description,
                    id = productEntity.id,
                    image = productEntity.image,
                    name = productEntity.name,
                    price = productEntity.price.toString(),
                    sku = productEntity.sku
                )
            )
        }
    }

    override fun remove(productEntity: ProductEntity): Completable {
        return Completable.fromAction {
            productDao.removeProduct(
                Product(
                    created_at = productEntity.created_at,
                    description = productEntity.description,
                    id = productEntity.id,
                    image = productEntity.image,
                    name = productEntity.name,
                    price = productEntity.price.toString(),
                    sku = productEntity.sku
                )
            )
        }
    }

    override fun saveAll(productsEntity: List<ProductEntity>): Completable {
        return Completable.fromAction {
            productDao.saveAllProducts(productsEntity.map {
                Product(
                    created_at = it.created_at,
                    description = it.description,
                    id = it.id,
                    image = it.image,
                    name = it.name,
                    price = it.price.toString(),
                    sku = it.sku
                )
            })
        }
    }

    override fun getAll(): Observable<List<ProductEntity>> {
        return Observable.just(productDao.getProducts().map { Utils.mapperProductDataToEntity(it) })
    }


    override fun get(productId: Int): Observable<Optional<ProductEntity>> {
        return Observable.fromCallable {
            val productData = productDao.get(productId)
            if (productData != null) {
                Optional.of(
                    ProductEntity(
                        created_at = productData.created_at,
                        description = productData.description,
                        id = productData.id,
                        image = productData.image,
                        name = productData.name,
                        price = productData.price.toDouble(),
                        sku = productData.sku
                    )
                )
            } else {
                Optional.empty()
            }

        }
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.just(productDao.getProducts().isEmpty())
    }
}