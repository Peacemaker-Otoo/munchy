package com.android.muncher.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.muncher.R
import com.android.muncher.databinding.FragmentSuccessfulResetBinding

class SuccessfulResetFragment : Fragment(R.layout.fragment_successful_reset) {

    private lateinit var binding: FragmentSuccessfulResetBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSuccessfulResetBinding.bind(view)

    }

}