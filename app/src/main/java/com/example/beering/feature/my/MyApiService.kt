package com.example.beering.feature.my

import retrofit2.Call
import retrofit2.http.GET

interface MyApiService {

    @GET("/members/me")
    fun getMy(): Call<MyResponse>
}