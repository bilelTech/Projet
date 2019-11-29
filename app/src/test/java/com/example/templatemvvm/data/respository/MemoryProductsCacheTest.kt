package com.example.templatemvvm.data.respository

import com.example.templatemvvm.data.repository.MemoryProductsCache
import com.example.templatemvvm.domain.models.ProductEntity
import com.example.templatemvvm.utils.TestsUtils
import org.junit.Test

class MemoryProductsCacheTest {

    private val memoryProductsCache: MemoryProductsCache = MemoryProductsCache()

    @Test
    fun clear() {
        memoryProductsCache.clear()
        assert(memoryProductsCache.products.isEmpty())
    }

    @Test
    fun save() {
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        memoryProductsCache.save(productEntity)
        assert(memoryProductsCache.products[productEntity.id] == productEntity)
    }

    @Test
    fun remove() {
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        memoryProductsCache.save(productEntity = productEntity)
        memoryProductsCache.remove(productEntity)
        assert(!memoryProductsCache.products.containsKey(productEntity.id))
    }

    @Test
    fun saveAll() {
        val list = TestsUtils.generateProductEntityList()
        memoryProductsCache.clear()
        memoryProductsCache.saveAll(list)
        assert(memoryProductsCache.products.size == 5)
    }

    @Test
    fun getAll() {
        val list = TestsUtils.generateProductEntityList()
        memoryProductsCache.saveAll(list)
        memoryProductsCache.getAll().test()
            .assertComplete()
            .assertValue { products ->
            products.size == 5
        }
    }

    @Test
    fun get() {
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        memoryProductsCache.save(productEntity)
        memoryProductsCache.get(productEntity.id).test().assertComplete().assertValue {
            it.value  == productEntity
        }
    }

    @Test
    fun isEmpty() {
        memoryProductsCache.clear()
        assert(memoryProductsCache.isEmpty())
    }
}