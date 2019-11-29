package com.example.templatemvvm.ui.dashboard.card.details

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class DetailCardActivityModule {
    @Provides
    internal fun providesViewModel(cardRepository: CardRepository): DetailCardViewModel {
        return DetailCardViewModel(cardRepository)
    }

    @Provides
    internal fun provideViewModelProvider(detailCardActivityViewModel: DetailCardViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(detailCardActivityViewModel)
    }
}