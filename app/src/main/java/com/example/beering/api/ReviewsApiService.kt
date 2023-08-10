package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET

interface ReviewsApiService {

    @GET("/reviews?page=")
    fun getReviews(): Call<ReviewsResponse>
}