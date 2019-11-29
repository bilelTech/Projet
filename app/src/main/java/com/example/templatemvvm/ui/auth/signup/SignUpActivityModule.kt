package com.example.templatemvvm.ui.auth.signup

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class SignUpActivityModule {

    @Provides
    internal fun providesViewModel(userRepository: UserRepositoryImpl): SignUpViewModel {
        return SignUpViewModel(userRepository)
    }

    @Provides
    internal fun provideViewModelProvider(viewModel: SignUpViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}