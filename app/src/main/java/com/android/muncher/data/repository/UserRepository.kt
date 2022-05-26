package com.android.muncher.data.repository

import android.view.View
import com.android.muncher.data.network.request.UserApi
import com.android.muncher.ui.util.snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) : BaseRepository(api) {

    suspend fun getLogin() = safeApiCall { api.loginUser() }
    suspend fun registerUser() = safeApiCall { api.registerUser() }

    suspend fun imageUpload(selectedImagePath: String?, userid: String) = safeApiCall {
        if (!selectedImagePath.equals("", ignoreCase = true)) {
            val file = selectedImagePath?.let { File(it) }
            val reqFile = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val body = reqFile?.let { MultipartBody.Part.createFormData("profile_picture", file.name, it) }
           api.uploadImg(body, userid)
        }else{
            val view: View? =null
            run {
                view?.snackbar("upload failed")
            }
        }
    }

}