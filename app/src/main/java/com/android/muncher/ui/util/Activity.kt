package com.android.muncher.ui.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.muncher.ui.util.FileUtils.context
import kotlin.system.exitProcess

//generic extension function to start new activity
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun Activity.dialogCloseOnBackPress(): OnBackPressedCallback {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Handle the back button event
            val dialog = AlertDialog.Builder(applicationContext).setMessage("").setNegativeButton(
                "No"
            ) { _, _ ->
                run {
                    Log.i("No", "don't close the app")
                }
            }
                .setPositiveButton("Ok") { _, _ ->
                    exitProcess(0)
                }.show()
            dialog.setMessage("Do you want to close ?")
        }
    }

    return onBackPressedCallback
}
