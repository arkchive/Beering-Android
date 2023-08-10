package com.example.beering.api


data class DrinkSearchResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: DrinkSearchResult
)

data class DrinkSearchResult(
    val content: List<Content>,
    val page: Int,
    val last: Boolean

)

data class Content(
    val drinkId: Int,
    val nameKr: String,
    val nameEn: String,
    val manufacturer: String,
    val imageUrlList: List<String>,
    val isLiked: Boolean
)


