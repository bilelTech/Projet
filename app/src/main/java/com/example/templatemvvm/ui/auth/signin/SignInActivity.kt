package com.example.templatemvvm.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.templatemvvm.R
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.di.factory.AppViewModelFactory
import com.example.templatemvvm.ui.dashboard.MainActivity
import com.example.templatemvvm.ui.auth.signup.SignUpActivity
import com.skydoves.githubfollows.extension.vm
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.input_email
import kotlinx.android.synthetic.main.activity_sign_in.input_password
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by lazy { vm(viewModelFactory, SignInViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        input_email.doAfterTextChanged { text -> viewModel.onChange(text.toString(), input_password.text.toString()); }
        input_password.doAfterTextChanged { text -> viewModel.onChange(input_email.text.toString(), text.toString()); }
        link_signup.setOnClickListener { goToSignUpActivity() }
        btn_login.setOnClickListener {
            viewModel.signIn(
                User(
                    12,
                    "",
                    input_email.text.toString(),
                    input_password.text.toString(),
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        viewModel.showProgress.observe(
            this,
            Observer {
                if (it == true)
                    progress.visibility = View.VISIBLE
                else
                    progress.visibility = View.GONE
            })
        viewModel.user.observe(this, Observer { if (it != null) goToMainActivity() else displayError() })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.btnSelected.observe(this, Observer { btn_login.isEnabled = it })
    }

    private fun displayError() {
        Toast.makeText(this, "error signin", Toast.LENGTH_SHORT).show()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}
