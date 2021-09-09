package com.sonicmaster.basicauth.model

data class User(
    val code: String,
    val `data`: Data,
    val message: String,
    val success: Boolean
)