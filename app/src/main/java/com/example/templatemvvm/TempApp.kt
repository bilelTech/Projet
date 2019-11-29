package com.example.templatemvvm

import com.crashlytics.android.Crashlytics
import com.example.templatemvvm.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.fabric.sdk.android.Fabric
import timber.log.Timber



class TempApp : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Fabric.with(this, Crashlytics())
        if (!LeakCanary.isInAnalyzerProcess(this)) {
           // LeakCanary.install(this)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

      //  Stetho.initializeWithDefaults(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}