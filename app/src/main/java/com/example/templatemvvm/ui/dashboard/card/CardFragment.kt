package com.example.templatemvvm.ui.dashboard.card

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.templatemvvm.R
import com.example.templatemvvm.adapters.CardsAdapter
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.dashboard.card.details.DetailCardActivity
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import com.vasl.recyclerlibrary.globalEnums.ListStatus
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_cards.*
import javax.inject.Inject

class CardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: CardViewModel
    private lateinit var cardsAdapter: CardsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CardViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getCards()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this , Observer {
            if (it != null) handleViewState(it)
            else displayEmptyView()
        })
        viewModel.errorState.observe(this , Observer { throwable ->
            throwable?.let {
                emptyHolder.visibility = View.VISIBLE
                emptyHolder.setStatus(ListStatus.FAILURE)
                cards_recyclerview.visibility = View.GONE
                progress.visibility = View.GONE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsAdapter = CardsAdapter { card ->
            navigateToCardsDetailsScreen(card)
        }
        cards_recyclerview.layoutManager = GridLayoutManager(activity!!, 2)
        cards_recyclerview.adapter = cardsAdapter
    }

    private fun displayEmptyView() {
        emptyHolder.visibility = View.VISIBLE
        emptyHolder.setStatus(ListStatus.EMPTY)
        cards_recyclerview.visibility = View.GONE
        progress.visibility = View.GONE
    }

    private fun navigateToCardsDetailsScreen(cardVO: CardVO) {
        startActivity(
            DetailCardActivity.newIntent(
                context!!,
                cardVO.id
            )
        )
    }

    private fun handleViewState(state: CardsViewState) {
        progress.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.cards?.let { cardsAdapter.addCards(it) }
    }


}