package com.example.templatemvvm.ui.dashboard.home.slider

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SliderFragmentProvider {
    internal abstract fun provideFavoritesFragmentFactory(): SliderFragment
}