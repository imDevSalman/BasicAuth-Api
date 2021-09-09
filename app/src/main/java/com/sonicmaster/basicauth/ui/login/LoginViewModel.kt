package com.sonicmaster.basicauth.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonicmaster.basicauth.model.Login
import com.sonicmaster.basicauth.model.Token
import com.sonicmaster.basicauth.network.Resource
import com.sonicmaster.basicauth.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<Login>> = MutableLiveData()
    val loginResponse: LiveData<Resource<Login>> = _loginResponse

    private val _tokenResponse: MutableLiveData<Resource<Token>> = MutableLiveData()
    val tokenResponse: LiveData<Resource<Token>> = _tokenResponse

    fun login(phone: String, password: String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(phone, password)
    }

    fun getTokenFromNetwork(username: String, password: String, grantType: String) =
        viewModelScope.launch {
            _tokenResponse.value = Resource.Loading
            _tokenResponse.value = repository.getTokenFromNetwork(username, password, grantType)
        }

    fun saveTokens(accessToken: String, refreshToken: String) = viewModelScope.launch {
        repository.saveToken(accessToken, refreshToken)
    }

}