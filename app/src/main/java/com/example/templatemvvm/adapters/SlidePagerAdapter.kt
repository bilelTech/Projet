package com.example.templatemvvm.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.templatemvvm.ui.dashboard.home.slider.SliderFragment

class SlidePagerAdapter constructor(fragmentManager: FragmentManager, private val sliders: ArrayList<String>) : FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return SliderFragment.newInstance(sliders[position])
    }

    override fun getCount(): Int {
        return sliders.size
    }
}