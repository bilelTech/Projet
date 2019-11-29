package com.example.templatemvvm.ui.dashboard.profile.models


    data class UserVO (var idUser: Long,
                       var name: String,
                       var email: String,
                       var password: String,
                       var zipCode: String,
                       var country: String,
                       var city: String,
                       var apiKey: String)