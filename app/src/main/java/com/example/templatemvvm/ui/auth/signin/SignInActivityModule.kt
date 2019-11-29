package com.example.templatemvvm.ui.auth.signin

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class SignInActivityModule {

    @Provides
    internal fun providesViewModel(userRepository: UserRepositoryImpl): SignInViewModel {
        return SignInViewModel(userRepository)
    }

    @Provides
    internal fun provideViewModelProvider(viewModel: SignInViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}