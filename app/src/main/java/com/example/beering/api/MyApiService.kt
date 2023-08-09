package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApiService {

    @GET("/members/me")
    fun getMy(): Call<MyResponse>
}