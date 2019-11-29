package com.example.templatemvvm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cards")
class CardData(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("description")
    var description: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("quantity")
    var quantity: Int? = 0
)