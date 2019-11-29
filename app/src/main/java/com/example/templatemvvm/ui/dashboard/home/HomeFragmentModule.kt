package com.example.templatemvvm.ui.dashboard.home

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Provides
    internal fun providesViewModel(productsRepositoryImpl: ProductsRepositoryImpl): HomeViewModel {
        return HomeViewModel(productsRepositoryImpl)
    }

    @Provides
    internal fun provideViewModelProvider(homeViewModel: HomeViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(homeViewModel)
    }
}