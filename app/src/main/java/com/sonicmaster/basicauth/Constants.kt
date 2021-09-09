package com.sonicmaster.basicauth

object Constants {
    const val ACCESS_TOKEN_KEY = "access_token"
    const val REFRESH_TOKEN_KEY = "refresh_token"

    const val BASE_URL = "https://api.mypharmacy4u.com/"
    const val BASIC_AUTH =
        "YXBwY2xpZW50c2VjcmV0OjdGRTU1NzYxLTFFNDMtNEJBOS05QjcxLUVDOUNGNDg4MDhCNg=="

    const val DATASTORE_NAME = "user_datastore"

    const val BASIC_AUTH_HEADER = "Basic"
    const val BEARER_AUTH_HEADER = "Bearer"

    enum class GrantType(val type: String) {
        PASSWORD(type = "password"),
        REFRESH_TOKEN(type = "refresh_token")
    }
}