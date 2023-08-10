package com.example.beering.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkSearchApiService {
    @GET("/drinks/search")
    fun drinkSearch(
        @Query("page") page: Int?,
        @Query("name") name: String?,
        @Query("orderBy") orderBy: String?,
        @Query("category") category: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?,
    ): Call<DrinkCoverResponse>
}