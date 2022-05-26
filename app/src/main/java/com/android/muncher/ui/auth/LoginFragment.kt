package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.android.muncher.R
import com.android.muncher.data.network.Resource
import com.android.muncher.databinding.FragmentLoginBinding
import com.android.muncher.ui.home.HomeControllerActivity
import com.android.muncher.ui.util.enable
import com.android.muncher.ui.util.handleApiError
import com.android.muncher.ui.util.startNewActivity
import com.android.muncher.ui.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.loader.visible(false)
        binding.loader.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.loader.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.user.access_token!!,
                            it.value.user.refresh_token!!
                        )
                        requireActivity().startNewActivity(HomeControllerActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
                else -> {
                }
            }
        })

        binding.loginEmail.addTextChangedListener {
            val email = binding.loginEmail.text.toString().trim()
            binding.loginBtn.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.registerBtn.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment2)
        }
        binding.forgotPass.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_emailVerificationFragment)
        }
    }

    private fun login() {
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()
        viewModel.login(email, password)
    }
}