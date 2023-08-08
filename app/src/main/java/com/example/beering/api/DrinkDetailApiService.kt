package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DrinkDetailApiService {

        @GET("/drinks/{drinkId}")
        fun getDrinkDetail(
            @Path("drinkId") drinkId : Int
        ): Call<DrinkDetailResponse>
}
