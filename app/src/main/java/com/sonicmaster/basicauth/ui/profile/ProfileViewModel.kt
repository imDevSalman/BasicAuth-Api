package com.sonicmaster.basicauth.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonicmaster.basicauth.model.User
import com.sonicmaster.basicauth.network.Resource
import com.sonicmaster.basicauth.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userResponse: MutableLiveData<Resource<User>> = MutableLiveData()
    val userResponse: LiveData<Resource<User>> = _userResponse

    fun getUser() = viewModelScope.launch {
        _userResponse.value = Resource.Loading
        _userResponse.value = repository.getUser()
    }

    fun removeTokens() {
        repository.removeTokens()
    }

}