package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewsApiService {

    @GET("/reviews?page=")
    fun getReviews(): Call<ReviewsResponse>

    @GET("/members/{memberId}/reviews/tabom?page=")
    fun getLikeReviews(
        @Path(value = "memberId") memberId : Int
    ): Call<ReviewsResponse>

}


