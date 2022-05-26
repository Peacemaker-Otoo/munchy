package com.android.muncher.ui.util

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.muncher.data.network.Resource
import com.android.muncher.ui.auth.LoginFragment
import com.android.muncher.ui.home.HomeControllerActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.system.exitProcess


fun Fragment.logout() = lifecycleScope.launch {
    if (activity is HomeControllerActivity) {
        (activity as HomeControllerActivity).performLogout()
    }
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Sorry, have problem with connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}


/**checking the current state of the graph*/
fun Fragment.checkNavDestination(checkCurrentDestination: Boolean) {
    var currentDestination = checkCurrentDestination
    currentDestination = false
    if (currentDestination && findNavController().currentDestination == null) {
        findNavController().navigate(findNavController().graph.startDestinationId)
    } else {
        currentDestination = false
    }
}


/**
 * A method to perform navigation
 * @param navHost : Pass the id nav_host_fragment in your xml file
 * @param action : Pass the id of the navigation direction from A to B
 * */
fun Fragment.navigateTo(action: Int) {
    // val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    // val navController = navHostFragment.navController
    //   navController.navigate(action)

}


fun Fragment.onBackPressedState(action: () -> Boolean) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            this.isEnabled = action()
            if (!this.isEnabled) {
                requireActivity().onBackPressed()
            }
        }
    })
}

/**call this on OnCreate method or onCreateView*/
fun Fragment.dialogCloseOnBackPress() {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Handle the back button event
            val dialog = AlertDialog.Builder(requireContext()).setMessage("").setNegativeButton(
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
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        onBackPressedCallback
    )
}

fun Fragment.snackBarCloseOnBackPress() {
    var backPressedTime: Long = 0
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Handle the back button event
            val backToast = Toast.makeText(
                requireActivity(),
                "Press back again to leave the app.",
                Toast.LENGTH_SHORT
            )
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel()
                exitProcess(1)
            } else {
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(
        this, onBackPressedCallback
    )
}

//call this in onActivity result
@Suppress("NAME_SHADOWING")
fun Fragment.uploadImg(selectedImagePath : String?, body: MultipartBody.Part?): MultipartBody.Part? {
    var body: MultipartBody.Part? = body
    if (!selectedImagePath.equals("", ignoreCase = true)) {
      val  file = selectedImagePath?.let { File(it) }
        val reqFile = file?.asRequestBody("image/*".toMediaTypeOrNull())
        body = reqFile?.let { MultipartBody.Part.createFormData("profile_picture", file.name, it) }
    }
    return body
}