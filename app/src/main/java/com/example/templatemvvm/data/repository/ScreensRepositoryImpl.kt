package com.example.templatemvvm.data.repository

import android.app.Application
import android.content.Context
import com.example.templatemvvm.data.local.ScreenDao
import com.example.templatemvvm.data.models.Screen
import com.example.templatemvvm.data.models.Screens
import com.example.templatemvvm.data.utils.Utils
import com.example.templatemvvm.domain.repository.ScreenRepository
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreensRepositoryImpl @Inject
constructor(
    private val application: Application,
    private val screenDao: ScreenDao
) : ScreenRepository {

    override fun getScreens(): Single<List<Screen>> {
        if (screenDao.getScreens().isEmpty()) {
            val gson = Gson()
            val string = Utils.readJSONFromAsset(application.applicationContext, "get-screens.json")
            val screens = gson.fromJson(string, Screens::class.java)
            screens.screens.map {
                screenDao.saveScreen(it)
            }
            return Single.just(screens.screens)
        } else {
            return Single.just(screenDao.getScreens())
        }
    }

    fun convert(string: String): Screens {
        val gson = Gson()
        return gson.fromJson(string, Screens::class.java)
    }

}