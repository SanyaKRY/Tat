package com.example.tat.features.login.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.tat.R
import com.example.tat.databinding.FragmentLoginBinding
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.presentation.vm.LoginViewModel
import com.example.tat.features.token.vm.TokenViewModel
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.presentation.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    private var alertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(requireContext()).setView(R.layout.loading).create()

        binding.imageViewLeftArrow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }

        binding.buttonLogin.setOnClickListener {
            val authApi = UserAuthApi(binding.editTextEmailAddress.text.toString().trim(),
                binding.editTextPassword.text.toString().trim())
            viewModel.login(authApi)
        }

        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { result ->

            when (result) {
                is Result.Success -> {
                    showAlertDialog(false)
                    result.data.let {
                        tokenViewModel.saveToken(it.accessToken, it.refreshToken)
                    }
                    startNewActivity()
                }
                is Result.Error -> showAlertDialog(false)
                is Result.Loading -> showAlertDialog(true)
            }
        })
    }

    private fun startNewActivity() {
        val activity = HomeActivity::class.java
        Intent(requireActivity(), activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun showAlertDialog(isLoading: Boolean) {
        if (isLoading) {
            alertDialog?.show()
        } else {
            alertDialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}