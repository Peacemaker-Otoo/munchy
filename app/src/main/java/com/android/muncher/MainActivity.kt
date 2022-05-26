package com.android.muncher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.android.muncher.data.network.source.UserPreferences
import com.android.muncher.ui.auth.AuthControllerActivity
import com.android.muncher.ui.home.HomeControllerActivity
import com.android.muncher.ui.onboarding.SlideControllerActivity
import com.android.muncher.ui.util.startNewActivity
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, Observer {
            lifecycleScope.launchWhenStarted {
                val activity =
                    if (it == null) SlideControllerActivity::class.java else HomeControllerActivity::class.java
                delay(3000)
                startNewActivity(activity)
            }
        })
    }
}