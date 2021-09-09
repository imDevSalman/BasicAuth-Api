package com.sonicmaster.basicauth.network

import com.sonicmaster.basicauth.BuildConfig
import com.sonicmaster.basicauth.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    fun <Api> buildApi(
        api: Class<Api>,
        authHeader: String,
        authToken: String
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Authorization", "$authHeader $authToken")
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}