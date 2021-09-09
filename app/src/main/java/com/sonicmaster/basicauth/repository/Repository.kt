package com.sonicmaster.basicauth.repository

import android.content.Context
import com.sonicmaster.basicauth.preferences.UserPreferences
import com.sonicmaster.basicauth.network.ApiInterface
import com.sonicmaster.basicauth.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}

class AuthRepository(private val apiInterface: ApiInterface, private val context: Context) :
    BaseRepository() {
    suspend fun login(phone: String, password: String) =
        safeApiCall { apiInterface.login(phone, password) }

    suspend fun getTokenFromNetwork(username: String, password: String, grantType: String) =
        safeApiCall { apiInterface.getToken(username, password, grantType) }


    fun saveToken(accessToken: String, refreshToken: String) {
        println("repo: $accessToken")
        println("repo: $refreshToken")
        UserPreferences.saveTokens(accessToken, refreshToken)
    }
}

class UserRepository(private val apiInterface: ApiInterface) :
    BaseRepository() {
    suspend fun getRefreshToken(refreshToken: String, grantType: String) = safeApiCall {
        safeApiCall { apiInterface.getRefreshToken(refreshToken, grantType) }
    }

    suspend fun getUser() =
        safeApiCall { apiInterface.getUser() }

    fun removeTokens() {
        UserPreferences.removeTokens()
    }

}