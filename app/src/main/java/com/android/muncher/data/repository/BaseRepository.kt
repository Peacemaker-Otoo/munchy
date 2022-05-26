package com.android.muncher.data.repository

import com.android.muncher.data.network.request.BaseApi
import com.android.muncher.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}