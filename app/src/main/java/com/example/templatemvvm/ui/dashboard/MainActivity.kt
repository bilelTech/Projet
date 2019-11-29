package com.example.templatemvvm.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.templatemvvm.R
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.auth.signin.SignInActivity
import com.example.templatemvvm.ui.dashboard.card.CardFragment
import com.example.templatemvvm.ui.dashboard.favorites.FavoritesFragment
import com.example.templatemvvm.ui.dashboard.home.HomeFragment
import com.example.templatemvvm.ui.dashboard.profile.ProfileFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment: Fragment
        loadFragment(HomeFragment())
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                }
                R.id.navigation_profile -> {
                    fragment = ProfileFragment()
                    loadFragment(fragment)
                }
                R.id.navigation_card -> {
                    fragment = CardFragment()
                    loadFragment(fragment)
                }
                R.id.navigation_fav -> {
                    fragment = FavoritesFragment()
                    loadFragment(fragment)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun openSignInActivity() {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> {
                openSignInActivity()
                return true
            }
        }
        return false
    }
}
