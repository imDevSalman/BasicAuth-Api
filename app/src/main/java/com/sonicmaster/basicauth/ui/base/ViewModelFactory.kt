package com.sonicmaster.basicauth.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonicmaster.basicauth.repository.AuthRepository
import com.sonicmaster.basicauth.repository.BaseRepository
import com.sonicmaster.basicauth.repository.UserRepository
import com.sonicmaster.basicauth.ui.login.LoginViewModel
import com.sonicmaster.basicauth.ui.profile.ProfileViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}