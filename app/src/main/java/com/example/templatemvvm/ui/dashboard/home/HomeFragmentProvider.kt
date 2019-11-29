package com.example.templatemvvm.ui.dashboard.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentProvider {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun provideHomeFragmentFactory(): HomeFragment
}