package com.example.templatemvvm.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @Provides
    internal fun providesViewModel(userRepository: UserRepositoryImpl): SplashViewModel {
        return SplashViewModel(userRepository)
    }

    @Provides
    internal fun provideViewModelProvider(viewModel: SplashViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}