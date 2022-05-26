package com.android.muncher.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.muncher.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthControllerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_controller)
    }
}