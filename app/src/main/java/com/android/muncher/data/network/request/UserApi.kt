package com.android.muncher.data.network.request

import com.android.muncher.data.responses.LoginResponse
import com.android.muncher.data.responses.RegisterResponse
import com.android.muncher.data.responses.User
import com.android.muncher.ui.util.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi : BaseApi {
    @POST("user")
    suspend fun loginUser(): LoginResponse

    @POST(Constants.Url.register)
    suspend fun registerUser(): RegisterResponse

    @Multipart
    @POST(Constants.Url.uploadImg)
    suspend fun uploadImg(
        @Part profile_picture: MultipartBody.Part?,
        @Part("name") salt: String
    ): User
}