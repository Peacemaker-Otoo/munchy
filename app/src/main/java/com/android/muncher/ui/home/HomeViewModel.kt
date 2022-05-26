package com.android.muncher.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.muncher.data.network.Resource
import com.android.muncher.data.repository.UserRepository
import com.android.muncher.data.responses.LoginResponse
import com.android.muncher.data.responses.RegisterResponse
import com.android.muncher.data.responses.User
import com.android.muncher.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _login: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val login: LiveData<Resource<LoginResponse>>
        get() = _login

    private val _register: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val register: LiveData<Resource<RegisterResponse>>
        get() = _register

    private val _upload: MutableLiveData<Resource<User?>> = MutableLiveData()
    val upload: LiveData<Resource<User?>>
        get() = _upload


    fun getUser() = viewModelScope.launch {
        _login.value = Resource.Loading
        _login.value = repository.getLogin()
    }

    fun registerUSer() = viewModelScope.launch {
        _register.value = Resource.Loading
        _register.value = repository.registerUser()
    }

    fun uploadImg(selectedImagePath: String?, userid: String) = viewModelScope.launch {
        _upload.value = Resource.Loading
        delay(3000)
        repository.imageUpload(selectedImagePath, userid)
        // _upload.postValue(repository.imageUpload(selectedImagePath, userid) as Resource<User>?)
    }

}