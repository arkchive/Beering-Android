package com.example.beering.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApiService {

    @POST("/auth/token")
    fun getToken(@Body tokenRequest: TokenRequest): Call<TokenResponse>

}