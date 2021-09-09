package com.sonicmaster.basicauth.model

data class Login(val success: Boolean, val message: String, val code: String, val data: LoginData)

data class LoginData(
    val id: String,
    val user_name: String,
    val user_email: String,
    val user_contact: String,
    val user_gender: String,
    val user_city: String,
    val user_nationality: String,
    val user_card_account_no: String,
    val user_state: String,
    val user_image: String,
    val user_password: String
)