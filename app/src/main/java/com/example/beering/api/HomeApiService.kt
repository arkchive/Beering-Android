package com.example.beering.api

import com.example.beering.api.ReviewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeApiService {

    @GET("/reviews?page=")
    fun getHome(): Call<ReviewsResponse>
}