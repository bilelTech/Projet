package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.data.models.Screen
import io.reactivex.Single

interface ScreenRepository {
    fun getScreens() : Single<List<Screen>>
}