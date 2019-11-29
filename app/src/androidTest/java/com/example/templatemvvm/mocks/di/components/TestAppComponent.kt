package com.example.templatemvvm.mocks.di.components

import android.app.Application
import com.example.templatemvvm.di.ActivityModule
import com.example.templatemvvm.di.AppComponent
import com.example.templatemvvm.di.ViewModelModule
import com.example.templatemvvm.mocks.di.modules.TestAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (ActivityModule::class),
        (ViewModelModule::class),
        (TestAppModule::class)]
)
interface TestAppComponent: AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}