package com.example.templatemvvm.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.templatemvvm.utils.TestsUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest : DBTest() {

    @Test
    fun saveAndGetProductsTest() {
        db.productDao().clear()
        TestsUtils.generateProductDataList()
        val list = TestsUtils.generateProductDataList()
        db.productDao().saveAllProducts(list)
        val listDb = db.productDao().getProducts()
        Assert.assertTrue(list.size == listDb.size)
    }

    @Test
    fun getProductTest() {
        val product = ProductData(1,"name1",10.0,"description","image")
        db.productDao().saveProduct(product)
        val productDb = db.productDao().get(1)
        Assert.assertTrue(product == productDb)
    }

    @Test
    fun removeProductTest() {
        val product = ProductData(1,"name1",10.0,"description","image")
        db.productDao().saveProduct(product)
        db.productDao().removeProduct(product)
        val productDb = db.productDao().get(1)
        Assert.assertNull(productDb)
    }
}