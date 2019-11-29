package com.example.templatemvvm.ui.dashboard.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.templatemvvm.R
import com.example.templatemvvm.adapters.NavigationAdapter
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.auth.signin.SignInActivity
import com.example.templatemvvm.ui.dashboard.profile.models.NavigationModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var navigationAdapter: NavigationAdapter
    internal var menus = arrayOf("Payment Method", "Reward Credits", "Settings", "Invite Friends")
    internal var icons =
        arrayOf(R.drawable.payment_method, R.drawable.reward_credits, R.drawable.settings, R.drawable.invite_friends)
    internal var chevron = arrayOf(
        R.drawable.ic_chevron,
        R.drawable.ic_chevron,
        R.drawable.ic_chevron,
        R.drawable.ic_chevron
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getUser()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.logoutState.observe(this, Observer {
            if (it == true) goToSignInActivity()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_rcv.layoutManager = LinearLayoutManager(activity!!)
        val navigationModels = ArrayList<NavigationModel>()

        for (i in icons.indices) {
            val ab = NavigationModel(menus[i], icons[i], chevron[i])
            navigationModels.add(ab)
        }
        navigationAdapter = NavigationAdapter { navigation ->
            Timber.d("navigation $navigation")
            viewModel.onClickMenu()
        }
        navigationAdapter.addNavigation(navigationModels)
        profile_rcv.adapter = navigationAdapter
    }

    private fun goToSignInActivity() {
        val intent = Intent(context, SignInActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}