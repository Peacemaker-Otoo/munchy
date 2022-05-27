package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.data.network.Resource
import com.android.muncher.databinding.FragmentRegisterBinding
import com.android.muncher.ui.util.enable
import com.android.muncher.ui.util.handleApiError
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        // binding.registerBtn.enable(false)//disabling the button when no input is made

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    register()
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {
                    kotlin.run { print("something went wrong") }
                }
            }
        })
        binding.registerEmail.addTextChangedListener {
            val email = binding.registerEmail.text.toString().trim()
            binding.registerBtn.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }


        binding.registerBtn.setOnClickListener {
            val checkBox = binding.terms
            if (checkBox.isChecked) {
                register()
                checkBox.isChecked = false
            } else {
                checkBox.text = getString(R.string.confirm_terms)
            }
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private fun register() {
        with(binding) {
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            val confirm = confirmPassword.text.toString().trim()
            viewModel.register(email, password, confirm)
        }

    }

}