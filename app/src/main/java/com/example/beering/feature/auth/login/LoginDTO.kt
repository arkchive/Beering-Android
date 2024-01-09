package com.example.beering.feature.auth.login

import com.example.beering.util.token.Jwt

// 로그인 request
data class LoginRequest(
    val username: String,
    val password: String
)

// 로그인 response
data class LoginResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: LoginResult
)

// result저장, erros는 따로 list형식으로 들어옴.
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

