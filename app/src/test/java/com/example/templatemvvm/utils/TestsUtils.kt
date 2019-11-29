package com.example.templatemvvm.utils

import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.data.models.Screen
import com.example.templatemvvm.domain.models.CardEntity
import com.example.templatemvvm.domain.models.ProductEntity

class TestsUtils {
    companion object {
        fun getMockedProductData(
            id: Int,
            name: String,
            price: Double,
            description: String,
            image: String
        ): ProductData {
            return ProductData(
                id = id,
                name = name,
                price = price,
                description = description,
                image = image
            )
        }

        fun generateProductDataList(): List<ProductData> {

            return (0..4).map {
                ProductData(
                    id = it,
                    name = "Product$it",
                    price = 1.0,
                    description = "desc$it",
                    image = "image$it"
                )
            }
        }

        fun generateProductData(): ProductData {

            return ProductData(
                id = 1,
                name = "Product1",
                price = 1.0,
                description = "desc1",
                image = "image1"
            )

        }

        fun generateProductEntityList(): List<ProductEntity> {

            return (0..4).map {
                ProductEntity(
                    id = it,
                    name = "Product$it",
                    price = 1.0,
                    description = "desc$it",
                    image = "image$it"
                )
            }
        }

        fun generateOnBoardingEntityList(): List<Screen> {
            return (0..4).map {
                Screen(
                    id = it,
                    title = "Screen$it",
                    description = "Desc$it",
                    screenImg = "Img$it"
                )
            }
        }

        fun generateCardsEntitysList(): List<CardEntity> {

            return (0..4).map {
                CardEntity(
                    id = it,
                    name = "Product$it",
                    price = 1.0,
                    description = "desc$it",
                    image = "image$it",
                    quantity = 1
                )
            }
        }

        fun generateCardsDataList(): List<CardData> {

            return (0..4).map {
                CardData(
                    id = it,
                    name = "Product$it",
                    price = 1.0,
                    description = "desc$it",
                    image = "image$it",
                    quantity = 1
                )
            }
        }
    }


}