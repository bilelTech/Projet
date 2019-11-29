package com.example.templatemvvm.data.local

import androidx.room.*
import com.example.templatemvvm.data.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id=:productId")
    fun get(productId: Int): Product?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllProducts(products: List<Product>)

    @Delete
    fun removeProduct(product: Product)

    @Query("DELETE FROM products")
    fun clear()
}