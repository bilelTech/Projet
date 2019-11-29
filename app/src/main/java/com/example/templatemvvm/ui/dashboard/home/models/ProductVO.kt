package com.example.templatemvvm.ui.dashboard.home.models


data class ProductVO(
    var id: Int,
    var name: String,
    var price: Double = 0.0,
    var description: String,
    var image: String
)
