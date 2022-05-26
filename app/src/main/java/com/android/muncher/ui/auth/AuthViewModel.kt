package com.android.muncher.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.muncher.data.network.Resource
import com.android.muncher.data.repository.AuthRepository
import com.android.muncher.data.responses.LoginResponse
import com.android.muncher.data.responses.RegisterResponse
import com.android.muncher.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse>>
        get() = _registerResponse


    fun login(email: String, password: String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun register(email: String, password: String, confirm: String) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(email, password, confirm)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        repository.saveAccessTokens(accessToken, refreshToken)
    }
}