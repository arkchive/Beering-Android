package com.example.beering.api

data class DrinkDetailResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val responseMessage : String,
    val result : DrinkDetailResult
)

data class DrinkDetailResult(
    val beerId : Int,
    val nameKr : String,
    val nameEn : String,
    val price : Int,
    val alcohol : Float,
    val description : String,
    val manufacturer : String,
    val totalRating : Float,
    val reviewCount : Int,
    val reviewPreviews : List<ReviewPreview>,
    val imageUrlList : List<String>,
    val liked : Boolean
)

data class ReviewPreview(
    val profile_image_url : Int,
    val nickname : String,
    val content : String,
    val createAt : String,
    val totalRating : Float
)