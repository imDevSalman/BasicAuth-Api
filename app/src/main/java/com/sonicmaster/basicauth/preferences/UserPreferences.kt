package com.sonicmaster.basicauth.preferences

import android.content.Context
import android.content.SharedPreferences
import com.sonicmaster.basicauth.Constants
import com.sonicmaster.basicauth.Constants.DATASTORE_NAME


object UserPreferences {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(DATASTORE_NAME, Context.MODE_PRIVATE)
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        val editor = sharedPreferences.edit()

        editor?.apply {
            putString(Constants.ACCESS_TOKEN_KEY, accessToken)
            putString(Constants.REFRESH_TOKEN_KEY, refreshToken)
        }?.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, null)
    }

    fun removeTokens() {

        val editor = sharedPreferences.edit()

        editor?.apply {
            remove(Constants.ACCESS_TOKEN_KEY)
            remove(Constants.REFRESH_TOKEN_KEY)
        }?.apply()
    }

}