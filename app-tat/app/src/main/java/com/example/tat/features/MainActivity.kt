package com.example.tat.features

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tat.R
import com.example.tat.features.home.presentation.ui.HomeActivity
import com.example.tat.features.login.presentation.ui.AuthActivity
import com.example.tat.features.token.vm.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val tokenViewModel by viewModels<TokenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tokenViewModel.accessToken.observe(this, Observer { accessToken ->
            val activity = if (accessToken == null) AuthActivity::class.java else HomeActivity::class.java

            Intent(this, activity).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        })
    }
}