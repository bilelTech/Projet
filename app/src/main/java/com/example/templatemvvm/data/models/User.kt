package com.example.templatemvvm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey var idUser: Long,
    var name: String,
    var email: String,
    var password: String,
    var zipCode: String,
    var country: String,
    var city: String,
    var apiKey: String
)