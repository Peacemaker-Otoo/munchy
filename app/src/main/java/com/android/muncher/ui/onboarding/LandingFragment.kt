package com.android.muncher.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.muncher.R
import com.android.muncher.databinding.FragmentLandingBinding


class LandingFragment : Fragment() {
    private var _binding: FragmentLandingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        with(binding) {
            loginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
            }
            registerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_landingFragment_to_registrationFragment)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}