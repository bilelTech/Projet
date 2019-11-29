package com.example.templatemvvm.domain.models

data class ProductEntity (
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: Double,
    val sku: String
)