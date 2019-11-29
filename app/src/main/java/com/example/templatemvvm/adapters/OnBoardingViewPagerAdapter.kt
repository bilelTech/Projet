package com.example.templatemvvm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.templatemvvm.R
import com.example.templatemvvm.ui.onboarding.ScreenItem

class OnBoardingViewPagerAdapter
    (val context: Context,val listScreen: List<ScreenItem>) : PagerAdapter() {


    override fun getCount(): Int = listScreen.size

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.item_screen, null)

        val imgSlide = layoutScreen.findViewById<ImageView>(R.id.intro_img)
        val title = layoutScreen.findViewById<TextView>(R.id.intro_title)
        val description = layoutScreen.findViewById<TextView>(R.id.intro_description)

        title.text = listScreen[position].title
        description.text = listScreen[position].description
        val id = context.resources.getIdentifier(listScreen[position].screenImg, "drawable", context.packageName)
        imgSlide.setImageResource(id)

        container.addView(layoutScreen)

        return layoutScreen


    }

    override fun isViewFromObject(@NonNull view: View, @NonNull o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {

        container.removeView(`object` as View)

    }
}
