package com.example.templatemvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.templatemvvm.data.models.CardData
import com.example.templatemvvm.data.models.Product
import com.example.templatemvvm.data.models.Screen
import com.example.templatemvvm.data.models.User

@Database(
    entities = arrayOf(User::class, Product::class, CardData::class, Screen::class),
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun cardDao(): CardDao
    abstract fun screenDao(): ScreenDao
}