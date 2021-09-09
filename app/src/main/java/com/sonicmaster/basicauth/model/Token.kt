package com.sonicmaster.basicauth.model

data class Token(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val refresh_token: String,
    val client_id: String,
    val username: String
)
