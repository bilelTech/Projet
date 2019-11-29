package com.example.templatemvvm.ui.dashboard.favorites

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
import com.example.templatemvvm.adapters.ProductsAdapter
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.dashboard.home.ProductsViewState
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductActivity
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import com.vasl.recyclerlibrary.globalEnums.ListStatus
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: ProductsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getFavorites()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
            else displayEmptyView()
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                emptyHolder.visibility = View.VISIBLE
                emptyHolder.setStatus(ListStatus.FAILURE)
                fav_recyclerview.visibility = View.GONE
                progress.visibility = View.GONE
            }
        })    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesAdapter = ProductsAdapter { product ->
            navigateToMovieDetailsScreen(product)
        }
        fav_recyclerview.layoutManager = GridLayoutManager(activity!!, 2)
        fav_recyclerview.adapter = favoritesAdapter
    }

    private fun displayEmptyView() {
        emptyHolder.visibility = View.VISIBLE
        emptyHolder.setStatus(ListStatus.EMPTY)
        fav_recyclerview.visibility = View.GONE
        progress.visibility = View.GONE
    }

    private fun navigateToMovieDetailsScreen(productVO: ProductVO) {
        startActivity(
            DetailProductActivity.newIntent(
                context!!,
                productVO.id
            )
        )
    }

    private fun handleViewState(state: ProductsViewState) {
        progress.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.products?.let { favoritesAdapter.addProducts(it) }
    }

}