package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.databinding.FragmentEmailVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment : Fragment(R.layout.fragment_email_verification) {
    private lateinit var binding: FragmentEmailVerificationBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmailVerificationBinding.bind(view)

        binding.sendCodeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_emailVerificationFragment_to_sendEmailFragment)
        }
    }

}