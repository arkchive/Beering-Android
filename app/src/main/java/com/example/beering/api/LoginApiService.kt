package com.example.beering.api

import com.example.beering.data.LoginRequest
import com.example.beering.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/auth/login")
    fun signIn(@Body loginRequest: LoginRequest): Call<LoginResponse>
}