package com.example.templatemvvm.ui.auth.signup

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
import com.skydoves.githubfollows.extension.vm
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by lazy { vm(viewModelFactory, SignUpViewModel::class) }
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        input_email.doAfterTextChanged { text -> }
        input_password.doAfterTextChanged { onChange() }
        input_name.doAfterTextChanged { onChange() }
        input_zip_code.doAfterTextChanged { onChange() }
        input_country.doAfterTextChanged { onChange() }
        input_city.doAfterTextChanged { onChange() }
        btn_signup.setOnClickListener {
            viewModel.signUp(
                User(
                    1,
                    input_name.text.toString(),
                    input_email.text.toString(),
                    input_password.text.toString(),
                    input_zip_code.text.toString(),
                    input_country.text.toString(),
                    input_city.text.toString(),
                    ""
                )
            )
        }
        viewModel.user.observe(this, Observer {
            if (it != null) {
                goToMainActivity()
            }
        })

        viewModel.showProgress.observe(
            this,
            Observer {
                if (it == true)
                    progress.visibility = View.VISIBLE
                else
                    progress.visibility = View.GONE
            })


        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.btnSelected.observe(this, Observer {
            if (it != null) {
                btn_signup.isEnabled = it
            }
        })
    }

    private fun onChange() {
        viewModel.onChangeText(
            input_name.text.toString(), input_email.text.toString(), input_password.text.toString(),
            input_zip_code.text.toString(), input_country.text.toString(), input_city.text.toString()
        );
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}
