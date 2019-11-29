package com.example.templatemvvm.ui.dashboard.card

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CardFragmentProvider {

    @ContributesAndroidInjector(modules = [CardFragmentModule::class])
    internal abstract fun provideCardFragmentFactory(): CardFragment
}