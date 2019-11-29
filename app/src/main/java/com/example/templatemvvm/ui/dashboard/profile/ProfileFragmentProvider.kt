package com.example.templatemvvm.ui.dashboard.profile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentProvider {
    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    internal abstract fun provideProfileFragmentFactory(): ProfileFragment
}