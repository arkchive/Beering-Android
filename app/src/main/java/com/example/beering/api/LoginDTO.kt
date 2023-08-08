package com.example.beering.data

// 로그인 request
data class LoginRequest(
    val username: String,
    val password: String,
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

// 토큰
data class Jwt(
    val accessToken: String,
    val refreshToken: String
)