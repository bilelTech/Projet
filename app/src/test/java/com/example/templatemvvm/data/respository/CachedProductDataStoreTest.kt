package com.example.templatemvvm.data.respository

import com.example.templatemvvm.data.repository.CachedProductDataStore
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.domain.repository.ProductsCache
import com.example.templatemvvm.domain.utils.Optional
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CachedProductDataStoreTest {
    private lateinit var productCache: ProductsCache
    private lateinit var cachedProductDataStore: CachedProductDataStore


    @Before
    fun before() {
        productCache = mock(ProductsCache::class.java)
        cachedProductDataStore = CachedProductDataStore(productCache)
    }

    @Test
    fun getProductByIdTest() {
        whenever(productCache.get(1)).thenReturn(
            Observable.just(
                Optional(
                    ProductEntity(
                        id = 1,
                        name = "name",
                        price = 1.0,
                        description = "description",
                        image = "image"
                    )
                )
            )
        )
        val testObservable = cachedProductDataStore.getProductById(1).test()
        testObservable.assertComplete().assertValue {
            it.value?.name == "name"
        }
    }
}