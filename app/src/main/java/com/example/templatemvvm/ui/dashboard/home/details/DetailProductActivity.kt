package com.example.templatemvvm.ui.dashboard.home.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.templatemvvm.R
import com.example.templatemvvm.adapters.PhotosAdapter
import com.example.templatemvvm.adapters.SlidePagerAdapter
import com.example.templatemvvm.di.factory.AppViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_detail_product.*
import timber.log.Timber
import javax.inject.Inject

class DetailProductActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var viewModel: DetailProductViewModel
    private lateinit var slidePagerAdapter: SlidePagerAdapter
    private lateinit var photosAdapter: PhotosAdapter

    companion object {
        const val PRODUCT_ID: String = "product_id"

        fun newIntent(context: Context, productId: Int?): Intent {
            val i = Intent(context, DetailProductActivity::class.java)
            i.putExtra(PRODUCT_ID, productId)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailProductViewModel::class.java)
        viewModel.getDetailProduct(12)
        slidePagerAdapter = SlidePagerAdapter(supportFragmentManager, arrayListOf("test", "test", "test"))
        viewpager.adapter = slidePagerAdapter
        photosAdapter = PhotosAdapter { photo ->
            Timber.d("photosAdapter onClick $photo")
        }
        photos_recyclview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photos_recyclview.adapter = photosAdapter

        viewModel.photoViewState.observe(this, Observer {
            photosAdapter.addPhotos(it)
        })
        viewModel.errorState.observe(this, Observer {
            Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}
