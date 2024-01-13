package com.example.beering.util.like

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LikeApiService {
    @POST("/members/{memberId}/drinks/{drinkId}/favorites")
    fun like(
        @Path(value = "memberId") memberId : Int,
        @Path(value = "drinkId") drinkId : Int
    ): Call<DrinkLikeResponse>

    @POST("/members/{memberId}/reviews/{reviewId}/tabom")
    fun reviewlike(
        @Path(value = "memberId") memberId: Int,
        @Path(value = "reviewId") reviewId: Int,
        @Query(value = "isUp") isUp: Boolean
    ): Call<DrinkLikeResponse>
}