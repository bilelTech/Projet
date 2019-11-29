package com.example.templatemvvm.di

import androidx.lifecycle.ViewModel
import com.example.templatemvvm.ui.auth.signin.SignInViewModel
import com.example.templatemvvm.ui.auth.signup.SignUpViewModel
import com.example.templatemvvm.ui.dashboard.card.CardViewModel
import com.example.templatemvvm.ui.dashboard.card.details.DetailCardViewModel
import com.example.templatemvvm.ui.dashboard.favorites.FavoritesViewModel
import com.example.templatemvvm.ui.dashboard.home.HomeViewModel
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductViewModel
import com.example.templatemvvm.ui.dashboard.profile.ProfileViewModel
import com.example.templatemvvm.ui.onboarding.OnBoardingViewModel
import com.example.templatemvvm.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindSignInActivityViewModel(signInActivityViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    internal abstract fun bindSignUpActivityViewModel(signUpActivityViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindSplashActivityViewModel(splashActivityViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeFragmentViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindProfileFragmentViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun bindFavoritesFragmentViewModel(favoritesFragmentViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CardViewModel::class)
    internal abstract fun bindCardFragmentViewModel(cardFragmentViewModel: CardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailProductViewModel::class)
    internal abstract fun bindDetailProductActivityViewModel(detailProductActivityViewModel: DetailProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailCardViewModel::class)
    internal abstract fun bindDetailCardActivityViewModel(detailCardActivityViewModel: DetailCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    internal abstract fun bindOnBoardingActivity(onBoardingViewModel: OnBoardingViewModel): ViewModel
}