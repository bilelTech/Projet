package com.example.templatemvvm.ui.dashboard.card

import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.data.repository.CardRepository
import com.example.templatemvvm.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class CardFragmentModule {
    @Provides
    internal fun providesViewModel(cardRepository: CardRepository): CardViewModel {
        return CardViewModel(cardRepository)
    }

    @Provides
    internal fun provideViewModelProvider(cardFragmentViewModel: CardViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(cardFragmentViewModel)
    }
}