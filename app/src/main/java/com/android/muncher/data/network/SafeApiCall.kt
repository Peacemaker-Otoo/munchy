package com.android.muncher.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(),"Http connection error", throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, ".........", null)
                    }
                }
            } catch (e: SocketTimeoutException) {
                Resource.Failure(false, null,errorMessage = e.message ?: "connection error!", null)
            } catch (e: ConnectException) {
                Resource.Failure(false, null,errorMessage = e.message ?: "no internet access",null)
            } catch (e: UnknownHostException) {
                Resource.Failure(false,null, errorMessage = e.message ?: "connection error", null)
            }
        }
    }
}