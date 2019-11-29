package com.example.templatemvvm.ui.dashboard.favorites

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesFragmentProvider {
    @ContributesAndroidInjector(modules = [FavoritesFragmentModule::class])
    internal abstract fun provideFavoritesFragmentFactory(): FavoritesFragment
}