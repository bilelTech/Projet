package com.example.templatemvvm.ui.dashboard.favorites

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.FavoritesRepository
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FavoritesFragmentModule {

    @Provides
    internal fun providesViewModel(favoritesRepository: FavoritesRepository): FavoritesViewModel {
        return FavoritesViewModel(favoritesRepository)
    }

    @Provides
    internal fun provideViewModelProvider(favoritesFragmentViewModel: FavoritesViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(favoritesFragmentViewModel)
    }
}