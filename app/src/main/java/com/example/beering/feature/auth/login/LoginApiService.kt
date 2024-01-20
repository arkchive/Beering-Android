package com.example.beering.feature.auth.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/auth/login")
    fun signIn(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/oauth/sdk")
    fun kakaoSignIn(@Body kakoLoginRequest: KakaoLoginRequest): Call<KakaoLoginResponse>
}