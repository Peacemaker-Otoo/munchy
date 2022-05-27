package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.databinding.FragmentSendEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendEmailFragment : Fragment(R.layout.fragment_send_email) {

    private lateinit var binding: FragmentSendEmailBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSendEmailBinding.bind(view)
        binding.openMail.setOnClickListener {
            findNavController().navigate(R.id.action_sendEmailFragment_to_recoveryCodeFragment)
        }

    }

}