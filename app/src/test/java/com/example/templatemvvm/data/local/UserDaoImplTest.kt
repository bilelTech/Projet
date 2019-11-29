package com.example.templatemvvm.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.templatemvvm.data.local.DBTest
import com.example.templatemvvm.data.models.User
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoImplTest : DBTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndGetUserTest() {
        db.userDao().insertUser(User(idUser = 1,email="test@gmail.com",password = "1234567890",username = "username",photourl = "phototurl"))
        val user = db.userDao().getUser()
        MatcherAssert.assertThat(user?.email, CoreMatchers.`is`("test@gmail.com"))
    }

    @Test
    fun clearTest() {
        db.userDao().clear()
        val list = db.userDao().getUser()
        Assert.assertTrue(list == null)
    }


}