package com.example.templatemvvm.data.respository

import com.example.templatemvvm.utils.TestsUtils
import com.example.templatemvvm.data.local.ProductDao
import com.example.templatemvvm.data.repository.FavoritesRepository
import com.example.templatemvvm.domain.models.ProductEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class FavoritesRepositoryTest {

    private lateinit var productDao: ProductDao
    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun before() {
        productDao = mock(ProductDao::class.java)
        favoritesRepository = FavoritesRepository(productDao)
    }

    @Test
    fun clear() {
        val testObserver = favoritesRepository.clear().test()
        verify(productDao).clear()
        // THEN
        testObserver.assertComplete()
    }

    @Test
    fun save() {
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = favoritesRepository.save(productEntity).test()
        verify(productDao).saveProduct(
            ProductData(
                id = productEntity.id,
                name = productEntity.name,
                price = productEntity.price,
                description = productEntity.description,
                image = productEntity.image
            )
        )
        testObserver.assertComplete()
    }

    @Test
    fun remove() {
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = favoritesRepository.remove(productEntity).test()
        verify(productDao).removeProduct(
            ProductData(
                id = productEntity.id,
                name = productEntity.name,
                price = productEntity.price,
                description = productEntity.description,
                image = productEntity.image
            )
        )
        testObserver.assertComplete()
    }

    @Test
    fun saveAll() {
        val list = TestsUtils.generateProductEntityList()
        val testObserver = favoritesRepository.saveAll(list).test()
        verify(productDao).saveAllProducts(list.map {
            ProductData(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                image = it.image
            )
        })
        testObserver.assertComplete()
    }

    @Test
    fun getAll() {
        val list = TestsUtils.generateProductEntityList()
        val testObserver = favoritesRepository.saveAll(list).test()
        testObserver.assertComplete()
        val testObservers = favoritesRepository.getAll().test()
        verify(productDao).getProducts()
        testObservers.assertComplete()
    }

    @Test
    fun getWhenProductDataIsNotNull() {
        val productData =
            ProductData(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        whenever(productDao.get(1)).thenReturn(productData)
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = favoritesRepository.get(productEntity.id).test()
        verify(productDao).get(productEntity.id)
        testObserver.assertComplete().assertValue { product-> product.value == productEntity }
    }

    @Test
    fun getWhenProductDataIsNull() {
        whenever(productDao.get(1)).thenReturn(null)
        val productEntity =
            ProductEntity(id = 1, name = "name", price = 1.0, description = "description", image = "image")
        val testObserver = favoritesRepository.get(productEntity.id).test()
        verify(productDao).get(productEntity.id)

        testObserver.assertComplete().assertValue { it.value == null}
    }

    @Test
    fun isEmpty() {
        whenever(productDao.getProducts()).thenReturn(emptyList())
        val testObserver = favoritesRepository.isEmpty().test()
        verify(productDao).getProducts()
        testObserver.assertComplete()
    }
}