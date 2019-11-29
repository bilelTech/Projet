package com.example.templatemvvm.data.mappers

data class SignInResponse(
    val apiKey: String,
    val city: String,
    val country: String,
    val createdAt: String,
    val email: String,
    val error: Boolean,
    val message: String,
    val name: String,
    val zipcode: String
)