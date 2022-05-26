package com.android.muncher.data.network.request

import com.android.muncher.data.responses.TokenResponse
import com.android.muncher.ui.util.Constants
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshApi : BaseApi {
    @FormUrlEncoded
    @POST(Constants.Url.refreshToken)
    suspend fun refreshAccessToken(
        @Field("refresh_token") refreshToken: String?
    ): TokenResponse
}