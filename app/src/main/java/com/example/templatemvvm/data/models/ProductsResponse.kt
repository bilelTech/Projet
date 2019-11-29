package com.example.templatemvvm.data.models

data class ProductsResponse(
    val error: Boolean,
    val products: List<Product>
)