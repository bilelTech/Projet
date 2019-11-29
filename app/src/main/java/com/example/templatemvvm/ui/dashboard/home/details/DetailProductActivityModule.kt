package com.example.templatemvvm.ui.dashboard.home.details

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.ProductsRepositoryImpl
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class DetailProductActivityModule {
    @Provides
    internal fun providesViewModel(productsRepositoryImpl: ProductsRepositoryImpl): DetailProductViewModel {
        return DetailProductViewModel(productsRepositoryImpl)
    }

    @Provides
    internal fun provideViewModelProvider(detailProductViewModel: DetailProductViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(detailProductViewModel)
    }
}