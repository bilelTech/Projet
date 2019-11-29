package com.example.templatemvvm.ui.dashboard.home

import com.example.templatemvvm.ui.dashboard.home.models.ProductVO

data class ProductsViewState(
    var showLoading: Boolean = true,
    var products: List<ProductVO>? = null
)