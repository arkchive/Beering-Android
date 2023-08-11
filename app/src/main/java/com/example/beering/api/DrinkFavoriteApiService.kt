package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DrinkFavoriteApiService {

    @GET("/members/{memberId}/favorites")
    fun drinkFavorite(
        @Path(value = "memberId") drinkId : Int,
        @Query("page") page: Int?
    ): Call<DrinkFavoriteResponse>
}