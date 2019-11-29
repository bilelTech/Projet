package com.example.templatemvvm.ui.onboarding

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.ScreensRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class OnBoardingModule {

    @Provides
    internal fun providesViewModel(screensRepositoryImpl: ScreensRepositoryImpl): OnBoardingViewModel {
        return OnBoardingViewModel(screensRepositoryImpl)
    }

    @Provides
    internal fun provideViewModelProvider(onBoardingViewModel: OnBoardingViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(onBoardingViewModel)
    }
}