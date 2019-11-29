package com.example.templatemvvm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("sku")
    val sku: String
)