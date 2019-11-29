package com.example.templatemvvm.data.local

import androidx.room.*
import com.example.templatemvvm.data.models.Screen

@Dao
interface ScreenDao {

    @Query("SELECT * FROM screens")
    fun getScreens(): List<Screen>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllScreens(screens: List<Screen>)

    @Insert
    fun saveScreen(screen: Screen)

    @Query("DELETE FROM screens")
    fun clear()
}