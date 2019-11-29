package com.example.templatemvvm.ui.dashboard.home.slider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.templatemvvm.R
import com.example.templatemvvm.di.factory.AppViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SliderFragment : Fragment() {
    private val ARG_SLIDER = "ARG_SLIDER"

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SLIDER, param1)
                }
            }
    }
}