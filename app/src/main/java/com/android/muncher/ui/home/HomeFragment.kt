package com.android.muncher.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.muncher.R
import com.android.muncher.data.network.Resource
import com.android.muncher.data.responses.User
import com.android.muncher.databinding.FragmentHomeBinding
import com.android.muncher.ui.util.handleApiError
import com.android.muncher.ui.util.logout
import com.android.muncher.ui.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)
        viewModel.getUser()

        viewModel.login.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value.user)
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })

        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    @Suppress("DEPRECATION")
    fun selectImg() {
        val intent: Intent? = null
        intent?.let {
            with(intent) {
                type = "image/*"
                action = Intent.ACTION_OPEN_DOCUMENT
                startActivityForResult(intent, IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            viewModel.uploadImg(data?.data?.path, "profile")
            //  binding.imageView.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun updateUI(user: User) {
        with(binding) {
            textViewId.text = user.id.toString()
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
    }

    companion object {
        const val IMAGE = 100
        val bitmap: Bitmap? = null
        const val REQUEST_CODE = 100
    }
}