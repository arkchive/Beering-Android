package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewWritingFormApiService {
    @GET ("/reviewOptions")
    fun getform(@Query("drinkId") drinkId: Int? ): Call<ReviewWritingFormResponse>
}