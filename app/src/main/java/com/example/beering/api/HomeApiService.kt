package com.example.beering.api

import retrofit2.Call
import retrofit2.http.POST

interface HomeApiService {

    @POST("/members/me")
    fun getHome(): Call<HomeResponse>
}