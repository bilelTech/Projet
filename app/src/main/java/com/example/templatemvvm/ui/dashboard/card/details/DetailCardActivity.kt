package com.example.templatemvvm.ui.dashboard.card.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.R
import com.example.templatemvvm.di.factory.AppViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailCardActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var  viewModel : DetailCardViewModel

    companion object {
        const val CARD_ID: String = "card_id"

        fun newIntent(context: Context, productId: Int?): Intent {
            val intent = Intent(context, DetailCardActivity::class.java)
            intent.putExtra(CARD_ID, productId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_card)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailCardViewModel::class.java)
        viewModel.getDetailCard(intent.getIntExtra(CARD_ID,0))
    }
}
