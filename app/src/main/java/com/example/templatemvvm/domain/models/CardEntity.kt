package com.example.templatemvvm.domain.models

data class CardEntity(
    var id: Int,
    var name: String,
    var price: Double = 0.0,
    var description: String,
    var image: String,
    var quantity: Int? = 0
)