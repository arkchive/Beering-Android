package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewDetailApiService {

    @GET("/reviews/{reviewId}")
    fun getReviewDetail(
        @Path("reviewId") reviewId : Int
    ): Call<ReviewDetailResponse>

}