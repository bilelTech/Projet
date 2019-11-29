package com.example.templatemvvm.ui.dashboard.card.models

data class CardVO (
    var id: Int,
    var name: String,
    var price: Double = 0.0,
    var description: String,
    var image: String,
    var quantity: Int? = 0
)