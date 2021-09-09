package com.sonicmaster.basicauth.network

import com.sonicmaster.basicauth.model.Login
import com.sonicmaster.basicauth.model.Token
import com.sonicmaster.basicauth.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("api/user/signin")
    suspend fun login(
        @Field("user_contact") userContact: String,
        @Field("user_password") userPassword: String
    ): Login

    @FormUrlEncoded
    @POST("token")
    suspend fun getToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Token

    @FormUrlEncoded
    @POST("token")
    suspend fun getRefreshToken(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String
    ): Token

    @GET("api/user")
    suspend fun getUser(): User
}