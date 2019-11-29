package com.example.templatemvvm.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.templatemvvm.R
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.dashboard.MainActivity
import com.example.templatemvvm.ui.auth.signin.SignInActivity
import com.example.templatemvvm.ui.onboarding.OnBoardingActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_splash)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashViewModel.SplashState.MainActivity -> {
                    goToMainActivity()
                }

                is SplashViewModel.SplashState.OnBoardIngActivity -> {
                    goToOnBoardingActivity()
                }
            }
        })
        Handler().postDelayed(
            {
                viewModel.checkUser()
            },
            3000 // value in milliseconds
        )
    }

    private fun goToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun goToOnBoardingActivity() {
        finish()
        startActivity(Intent(this, OnBoardingActivity::class.java))
    }
}
