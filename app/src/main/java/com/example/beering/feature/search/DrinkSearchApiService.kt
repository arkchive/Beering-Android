package com.example.beering.feature.search

import com.example.beering.util.data.DrinkCoverResponse
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
        @Query("country") country: String?,
        @Query("tag") tag: String?,
        @Query("sweetness") sweetness: Int?
    ): Call<DrinkCoverResponse>
}