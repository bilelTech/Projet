package com.example.templatemvvm.ui.dashboard.card

import com.example.templatemvvm.ui.dashboard.card.models.CardVO

data class CardsViewState (
    var showLoading: Boolean = true,
    var cards: List<CardVO>? = null
)