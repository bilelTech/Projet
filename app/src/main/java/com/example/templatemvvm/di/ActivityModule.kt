package com.example.templatemvvm.di

import com.example.templatemvvm.ui.dashboard.MainActivity
import com.example.templatemvvm.ui.auth.signin.SignInActivity
import com.example.templatemvvm.ui.auth.signin.SignInActivityModule
import com.example.templatemvvm.ui.auth.signup.SignUpActivity
import com.example.templatemvvm.ui.auth.signup.SignUpActivityModule
import com.example.templatemvvm.ui.dashboard.card.CardFragmentProvider
import com.example.templatemvvm.ui.dashboard.card.details.DetailCardActivity
import com.example.templatemvvm.ui.dashboard.card.details.DetailCardActivityModule
import com.example.templatemvvm.ui.dashboard.favorites.FavoritesFragmentProvider
import com.example.templatemvvm.ui.dashboard.home.HomeFragmentProvider
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductActivity
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductActivityModule
import com.example.templatemvvm.ui.dashboard.home.slider.SliderFragmentProvider
import com.example.templatemvvm.ui.dashboard.profile.ProfileFragmentProvider
import com.example.templatemvvm.ui.onboarding.OnBoardingActivity
import com.example.templatemvvm.ui.onboarding.OnBoardingModule
import com.example.templatemvvm.ui.splash.SplashActivity
import com.example.templatemvvm.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [SignUpActivityModule::class])
    internal abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector(modules = [SignInActivityModule::class])
    internal abstract fun contributeSignInActivity(): SignInActivity

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(
        modules = [
            HomeFragmentProvider::class,
            ProfileFragmentProvider::class,
            FavoritesFragmentProvider::class,
            CardFragmentProvider::class]
    )
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [DetailProductActivityModule::class,
                   SliderFragmentProvider::class]
    )
    internal abstract fun contributeDetailProductActivity(): DetailProductActivity

    @ContributesAndroidInjector(modules = [DetailCardActivityModule::class])
    internal abstract fun contributeDetailCardActivity(): DetailCardActivity

    @ContributesAndroidInjector(modules =[OnBoardingModule::class])
    internal abstract fun contributeOnBoardingActivity(): OnBoardingActivity
}