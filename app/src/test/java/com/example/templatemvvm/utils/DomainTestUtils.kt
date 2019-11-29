package com.example.templatemvvm.utils

import com.example.templatemvvm.domain.models.ProductEntity

class DomainTestUtils {

    companion object {
        fun getTestProductEntity(id: Int): ProductEntity {
            return ProductEntity(
                id = id,
                name = "Name$id",
                price = 1.0,
                description = "description$id",
                image = "image$id"
            )
        }

        fun getTestProductEntityWithDetails(id: Int): ProductEntity {
            val productsEntity = getTestProductEntity(id)
            return productsEntity
        }

        fun generateProductsEntityList(): List<ProductEntity> {
            return (0..4).map { getTestProductEntity(it) }
        }
    }
}