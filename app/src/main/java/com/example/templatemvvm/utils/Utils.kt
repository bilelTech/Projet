package com.example.templatemvvm.utils

import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.data.models.Product
import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.domain.models.ProductEntity

class Utils {
    companion object {

        fun mapperProductDataToEntity(product: Product): ProductEntity {
            return ProductEntity(
                created_at = product.created_at,
                description = product.description,
                id = product.id,
                image = product.image,
                name = product.name,
                price = product.price.toDouble(),
                sku = product.sku
            )
        }

        fun mapperCardDataToEntity(cardData: CardData): CardEntity {
            return CardEntity(
                id = cardData.id,
                name = cardData.name,
                price = cardData.price,
                description = cardData.description,
                image = cardData.image,
                quantity = cardData.quantity
            )
        }
    }
}