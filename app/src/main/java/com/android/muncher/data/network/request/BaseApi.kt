package com.android.muncher.data.network.request

import com.android.muncher.ui.util.Constants
import okhttp3.ResponseBody
import retrofit2.http.POST

interface BaseApi {
    @POST(Constants.Url.login)
    suspend fun logout(): ResponseBody
}