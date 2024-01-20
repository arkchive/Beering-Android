package com.example.beering.feature.review.reviewWriting

import com.example.beering.feature.review.ReviewWritingFormResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewWritingFormApiService {
    @GET ("/reviewOptions")
    fun getform(@Query("drinkId") drinkId: Int? ): Call<ReviewWritingFormResponse>
}