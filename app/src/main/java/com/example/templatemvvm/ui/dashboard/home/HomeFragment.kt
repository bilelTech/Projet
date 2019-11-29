package com.example.templatemvvm.ui.dashboard.home

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
import com.example.templatemvvm.ui.dashboard.home.details.DetailProductActivity
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import com.vasl.recyclerlibrary.globalEnums.ListStatus
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var productsAdapter: ProductsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getProducts()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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
                products_recyclerview.visibility = View.GONE
                progress.visibility = View.GONE
            }
        })
        card_bt.setOnClickListener {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsAdapter = ProductsAdapter { product ->
            navigateToMovieDetailsScreen(product)
        }
        products_recyclerview.layoutManager = GridLayoutManager(activity!!, 2)
        products_recyclerview.adapter = productsAdapter
    }

    private fun displayEmptyView() {
        emptyHolder.visibility = View.VISIBLE
        emptyHolder.setStatus(ListStatus.EMPTY)
        products_recyclerview.visibility = View.GONE
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
        state.products?.let { productsAdapter.addProducts(it) }
    }
}