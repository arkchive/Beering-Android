package com.example.beering.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeApiService {
    @POST("/members/{memberId}/drinks/{drinkId}/favorites")
    fun like(
        @Path(value = "memberId") memberId : Int,
        @Path(value = "drinkId") drinkId : Int
    ): Call<DrinkLikeResponse>
}