package com.example.beering.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String,
)


data class LoginResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: LoginResult
)

data class LoginResult(
    val memberId: Int,
    val jwtInfo: Jwt,
    val errors : List<LoginError>
)
data class LoginError(
    val fieldName : String,
    val rejectValue : String,
    val message : String
)

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)