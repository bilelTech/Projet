package com.example.templatemvvm.ui.dashboard.profile

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.UserRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {

    @Provides
    internal fun providesViewModel(userRepositoryImpl: UserRepositoryImpl): ProfileViewModel {
        return ProfileViewModel(userRepositoryImpl)
    }

    @Provides
    internal fun provideViewModelProvider(profileViewModel: ProfileViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(profileViewModel)
    }
}