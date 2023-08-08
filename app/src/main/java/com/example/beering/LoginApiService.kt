package com.example.beering

import com.example.beering.data.LoginRequest
import com.example.beering.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/auth/login")
    fun signUp(@Body loginRequest: LoginRequest): Call<LoginResponse>
}