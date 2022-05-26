package com.android.muncher.data.network.request

import com.android.muncher.data.responses.LoginResponse
import com.android.muncher.data.responses.RegisterResponse
import com.android.muncher.ui.util.Constants
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @FormUrlEncoded
    @POST(Constants.Url.login)
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse


    @POST(Constants.Url.register)
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm") confirmPassword: String
    ): RegisterResponse


}