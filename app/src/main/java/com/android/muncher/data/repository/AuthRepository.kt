package com.android.muncher.data.repository

import com.android.muncher.data.network.request.AuthApi
import com.android.muncher.data.network.source.UserPreferences
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun register(
        email: String,
        password: String,
        confirm: String
    ) = safeApiCall {
        api.register(email, password, confirm)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }

}