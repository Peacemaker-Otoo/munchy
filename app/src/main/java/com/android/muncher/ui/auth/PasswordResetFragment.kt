package com.android.muncher.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.databinding.FragmentPasswordResetBinding


class PasswordResetFragment : Fragment(R.layout.fragment_password_reset) {
    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordResetBinding.bind(view)

        binding.sendCodeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_passwordResetFragment_to_successfulResetFragment)
        }
    }


}