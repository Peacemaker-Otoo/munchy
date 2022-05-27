package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.databinding.FragmentRecoveryCodeBinding


class RecoveryCodeFragment : Fragment(R.layout.fragment_recovery_code) {

    private lateinit var binding: FragmentRecoveryCodeBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecoveryCodeBinding.bind(view)
        binding.openMail.setOnClickListener {
            findNavController().navigate(R.id.action_recoveryCodeFragment_to_passwordResetFragment)
        }
    }


}