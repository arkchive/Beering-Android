package com.example.beering.feature.home

import retrofit2.Call
import retrofit2.http.GET

interface ReviewsApiService {

    @GET("/reviews?page=")
    fun getReviews(): Call<ReviewsResponse>
}