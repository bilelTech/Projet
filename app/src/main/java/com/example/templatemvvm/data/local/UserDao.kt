package com.example.templatemvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.templatemvvm.data.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM users")
    fun clear()

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): User?
}