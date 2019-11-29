package com.example.templatemvvm.data.repository

import com.example.templatemvvm.data.local.UserDao
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductDataStore
import com.example.templatemvvm.domain.repository.ProductsCache
import com.example.templatemvvm.domain.repository.ProductsRepository
import com.example.templatemvvm.domain.utils.Optional
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject
constructor(
    private val remoteService: RemoteService,
    private val productsCache: ProductsCache,
    private val userDao: UserDao
) : ProductsRepository {

    private val memoryDataStore: ProductDataStore
    private val remoteDataStore: ProductDataStore

    init {
        memoryDataStore = CachedProductDataStore(productsCache)
        remoteDataStore = RemoteProductsDataSource(remoteService,userDao)
    }

    override fun getProducts(): Observable<List<ProductEntity>> {
        return if (!productsCache.isEmpty()) {
            return memoryDataStore.getProduct()
        } else {
            remoteDataStore.getProduct().doOnNext { productsCache.saveAll(it) }
        }
    }

    override fun getProduct(productId: Int): Observable<Optional<ProductEntity>> {
        return remoteDataStore.getProductById(productId)
    }
}