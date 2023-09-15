package com.example.tat.features.home.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.tat.R
import com.example.tat.databinding.ActivityHomeBinding
import com.example.tat.features.home.presentation.vm.HomeViewModel
import com.example.tat.features.login.presentation.ui.AuthActivity
import com.example.tat.features.token.vm.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val tokenViewModel: TokenViewModel by viewModels()
    private val viewModel by viewModels<HomeViewModel>()

    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_home_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun performLogout() {
        tokenViewModel.deleteToken()
        viewModel.logout()

        val activity = AuthActivity::class.java
        Intent(this, activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "fun onDestroy")
    }
}